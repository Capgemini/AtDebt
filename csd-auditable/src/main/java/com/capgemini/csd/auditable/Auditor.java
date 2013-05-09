/*
 * @Debt Copyright (C) 2010 Andrew Harmel-Law
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * To contact the development team, please refer to the project site at:
 *
 *      http://kenai.com/projects/csdutilities
 */
package com.capgemini.csd.auditable;

import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.capgemini.csd.auditable.Auditable.Statement;


/**
 * An AOP Aspect that handles auditing of services that match the 
 * {@link Auditable}, {@link Auditable.Statement} annotations.
 * 
 */
@Aspect
public class Auditor 
{
    private static final Log LOGGER = LogFactory.getLog(Auditor.class); 
    
    private AuditMessager messager;
    
    public Auditor(AuditMessager messager)
    {
        this.messager = messager;
    }
    
    /**
     * Audits a method that is annotated with {@link Auditable}, and has a
     * parameter of type {@link AuditableEntity}.
     *  
     * @param jp
     * @param auditable The annotation instance
     * @param auditableEntity The entity you want to audit.
     */
    @After("@annotation(auditable) && args(auditableEntity,..)")
    public void auditEntity(JoinPoint jp, Auditable auditable, AuditableEntity auditableEntity)
    {
        doAudit(jp,auditable.operation(),auditableEntity);
    }
    
    /**
     * Audits a method that is annotated with {@link Auditable.ReturnEntity}, and has a
     * return type {@link AuditableEntity}.
     *  
     * @param jp
     * @param auditable The annotation instance
     * @param auditableEntity The entity you want to audit.
     */
    @AfterReturning(pointcut = "@annotation(auditable)", returning = "auditableEntity")
    public void auditReturnedEntity(JoinPoint jp, Auditable.ReturnEntity auditable, 
                                                  AuditableEntity auditableEntity)
    {
            doAudit(jp,auditable.operation(),auditableEntity);
    }

    /**
     * Audits a method annotated with {@link Auditable.Statement}.
     * 
     * @param jp
     * @param statement The audit statement to log.
     */
    @After("@annotation(statement)")
    public void auditStatement(JoinPoint jp, Auditable.Statement statement)
    {
        Audit audit = getAuditInstance(jp);
        audit.setOperation(Statement.OPERATION_TYPE);
        audit.setCallerId(Statement.STATEMENT_OWNER);
        audit.setStatement(statement.value());
        
        LOGGER.info("Audit Statement - " + audit.toString());
        messager.message(audit);
    }

    /**
     * Returns an {@link Audit} instance with default values set.
     * 
     * @param jp
     * @return
     */
    private Audit getAuditInstance(JoinPoint jp)
    {
        // Create audit object.
        Audit audit = new Audit();
        audit.setId(getUUID());
        audit.setTimestamp(Calendar.getInstance().getTime());
        audit.setOrigin(jp.getSignature().toShortString());
        
        return audit;
    }

    private void doAudit(JoinPoint jp, String operation, AuditableEntity auditableEntity)
    {
        Audit audit = getAuditInstance(jp);
        audit.setOperation(operation);
        audit.setCallerId(auditableEntity.getCallerId());
        audit.setSubject(auditableEntity);
        
        LOGGER.info("Audit Entity - " + audit.toString());
        messager.message(audit);
    }
     
    /**
     * Generates a UUID.
     *  
     * @return a newly generated UUID instance.
     */
    private static final UUID getUUID()
    {
        UUID uuid = UUID.randomUUID();
        //LOGGER.debug("Generated random UUID: " + uuid.toString());
        
        return uuid;
    }
}
