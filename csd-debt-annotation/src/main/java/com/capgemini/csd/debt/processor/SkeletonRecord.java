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
 *   http://kenai.com/projects/csdutilities
 */
package com.capgemini.csd.debt.processor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import javax.lang.model.element.Element;

import com.capgemini.csd.debt.Debt;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public abstract class SkeletonRecord {

    protected String name;
    protected Set<Debt> debtRecords;
    protected SkeletonRecord parent;

    public String getName() {
        return name;
    }

    public SkeletonRecord getParentRecord() {
        return parent;
    }

    public abstract String getFileName();
    
    protected boolean recordDebt(Debt debt) {
        boolean resultIndicator = debtRecords.add(debt);
        return resultIndicator;
    }

    protected boolean represents(Element element) {

        return this.name.equals(element.toString());
    }

    /**
     * <P><B>NOTE</B>Taken from: 
     * http://www.javapractices.com/topic/TopicAction.do?Id=96
     * 
     * <P>Escape characters for text appearing as XML data, between tags.
     * 
     * <P>The following characters are replaced with corresponding character entities :
     * <table border='1' cellpadding='3' cellspacing='0'>
     *     <tr><th> Character </th><th> Encoding </th></tr>
     *     <tr><td> < </td><td> &lt; </td></tr>
     *     <tr><td> > </td><td> &gt; </td></tr>
     *     <tr><td> & </td><td> &amp; </td></tr>
     *     <tr><td> " </td><td> &quot;</td></tr>
     *     <tr><td> ' </td><td> &#039;</td></tr>
     * </table>
     *
     * <P>Note that JSTL's {@code <c:out>} escapes the exact same set of 
     * characters as this method. <span class='highlight'>That is, {@code <c:out>}
     * is good for escaping to produce valid XML, but not for producing safe 
     * HTML.</span>
     */
    public static String forXML(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '\"') {
                result.append("&quot;");
            } else if (character == '\'') {
                result.append("&#039;");
            } else if (character == '&') {
                result.append("&amp;");
            } else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SkeletonRecord other = (SkeletonRecord) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.debtRecords != other.debtRecords && (this.debtRecords == null || !this.debtRecords.equals(other.debtRecords))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 89 * hash + (this.debtRecords != null ? this.debtRecords.hashCode() : 0);
        return hash;
    }
}
