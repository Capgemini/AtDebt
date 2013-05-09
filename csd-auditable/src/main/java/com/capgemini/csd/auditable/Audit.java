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

import java.util.Date;
import java.util.UUID;

/**
 * Holds audit information that is to be persisted.
 * 
 * @author Andrew Harmel-Law
 * @author Wadud Ruf
 *
 */
public class Audit
{
    private UUID id;					// A unique identifier for this audit instance.
    private Date timestamp;				// the time-stamp of the audit message.
    private String callerId;			// The caller performing the operation.
    
    private String operation;			// The type of operation being performed.
    private String statement;			// the audit statement to be logged.
    private String origin;				// The origin of the audit message.
    private AuditableEntity subject; 	// The subject of the audit.
     
	public Audit()
    {
        
    }
    
	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getStatement()
	{
		return statement;
	}

	public void setStatement(String statement)
	{
		this.statement = statement;
	}

	public String getOrigin()
	{
		return origin;
	}

	public void setOrigin(String origin)
	{
		this.origin = origin;
	}

	public Date getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(Date timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getCallerId()
	{
		return callerId;
	}

	public void setCallerId(String callerId)
	{
		this.callerId = callerId;
	}
	   
    public AuditableEntity getSubject()
	{
		return subject;
	}

	public void setSubject(AuditableEntity subject)
	{
		this.subject = subject;
	}

	@Override
    public String toString() {
        String str = "[" + id.toString() + "] [" + operation + "], " +
                     "[Caller: " + callerId + "] [" + timestamp + 
                     "] [" + origin + "]";
        
        if(statement != null)
            str += " [" + statement + "]";
        
        if(subject != null)
            str += " [" + subject.toString() + "]";
        
        return str;
    }
}
