/**
 *
 * Copyright (c) 2014, the Railo Company Ltd. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package lucee.runtime.functions.displayFormatting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import lucee.print;
import lucee.commons.lang.StringUtil;
import lucee.runtime.PageContext;
import lucee.runtime.engine.ThreadLocalPageContext;
import lucee.runtime.exp.CasterException;
import lucee.runtime.exp.PageException;
import lucee.runtime.ext.function.Function;
import lucee.runtime.op.date.DateCaster;
import lucee.runtime.type.dt.DateTime;

/**
 * Implements the CFML Function dateformat
 */
public final class DateFormat implements Function {
	
	public static String call(PageContext pc , Object object) throws PageException {
		return _call(pc,object,"dd-mmm-yy",ThreadLocalPageContext.getTimeZone(pc));
	}
	public static String call(PageContext pc , Object object, String mask) throws PageException {
		return _call(pc, object, mask, ThreadLocalPageContext.getTimeZone(pc));
	}
	public static String call(PageContext pc , Object object, String mask,TimeZone tz) throws PageException {
		return _call(pc, object, mask, tz==null?ThreadLocalPageContext.getTimeZone(pc):tz);
	}
	private static String _call(PageContext pc , Object object, String mask,TimeZone tz) throws PageException {
	    Locale locale=Locale.US;
	    
		DateTime datetime = DateCaster.toDateAdvanced(object,tz,null);
			//Caster.toDate(object,true,tz,null);
		if(datetime==null) {
			if(StringUtil.isEmpty(object,true)) return "";
		    throw new CasterException(object,"datetime");
		    //if(!Decision.isSimpleValue(object))
		    //    throw new ExpressionException("can't convert object of type "+Type.getName(object)+" to a datetime value");
		    //throw new ExpressionException("can't convert value "+object+" to a datetime value");
		}
		return new lucee.runtime.format.DateFormat(locale).format(datetime,mask,tz);
	}
}