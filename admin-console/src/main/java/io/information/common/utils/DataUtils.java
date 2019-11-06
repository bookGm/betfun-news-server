package io.information.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

import com.guansuo.common.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class DataUtils {
       //copy Data
		public static <T,K> List<T> copyHousekeepData(Class<T> c,List<K> ks){
			if(null!=ks){
				List<T> list=new ArrayList<T>();
				ks.forEach(n->{
					T t=null;
					try {
						t = c.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
					BeanUtils.copyProperties(n, t);
					list.add(t);
				});
				return list;
			}
			return null;
		}
		
		//copy Data
		public static <T,K>K copyData(T c,Class<K> k){
			K a=null;
			try {
				a=k.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			BeanUtils.copyProperties(c, a);
			return a;
		}
		
		/**
	     * Object转BigDecimal
	     * @param value
	     * @return
	     */
	    public static BigDecimal objectConvertBigDecimal(Object value) {
	    	BigDecimal ret = null;
	    	if (value != null) {
		    	if (value instanceof BigDecimal) {
		    	  ret = (BigDecimal) value;
		    	} else if (value instanceof String) {
		    	  ret = new BigDecimal((String) value);
		    	} else if (value instanceof BigInteger) {
		    	  ret = new BigDecimal((BigInteger) value);
		    	} else if (value instanceof Number) {
		    	  ret = new BigDecimal(((Number) value).doubleValue());
		    	} else {
		    	  throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
		    	  + " into a BigDecimal.");
		    	}
	        }
	        return ret;
	    }
	    public static void main(String[] args) {
			System.out.println(objectConvertBigDecimal(2.02));
		}
	    /**
	     * Object转Integer
	     * @param object
	     * @return
	     */
	    public static Integer getIntegerByObject(Object object){
    	   Integer in = 0;
    	   if(object!=null){
    	      if(object instanceof Integer){
    	         in = (Integer)object;
    	      }else if(object instanceof String){
    	         in = Integer.parseInt((String)object);
    	      }else if(object instanceof Double){
    	         in = (int)((double)object);
    	      }else if(object instanceof Float){
    	         in = (int)((float)object);
    	      }else if(object instanceof BigDecimal){
    	         in = ((BigDecimal)object).intValue();
    	      }else if(object instanceof Long){
    	         in = ((Long)object).intValue();
    	      }
    	   }
    	   
    	   return in;
    	}
	    
		/**
		 * 覆盖合并
		 * @param <T>
		 * @param source
		 * @param target
		 * @return
		 */
		public static <T> T coverMergerBean(Object source,T target){
			BeanUtils.copyProperties(source, target, getValuePropertyNames(source));
			return target;
		}
		
		
		/**
		 * 获取null字段
		 * @param source
		 * @return
		 */
		public static String[] getValuePropertyNames (Object source) {
		        final BeanWrapper src = new BeanWrapperImpl(source);
		        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		        Set<String> emptyNames = new HashSet<>();
		        for(java.beans.PropertyDescriptor pd : pds) {
		            Object srcValue = src.getPropertyValue(pd.getName());
		            if (null == srcValue) emptyNames.add(pd.getName());
		        }
		        String[] result = new String[emptyNames.size()];
		        return emptyNames.toArray(result);
		}

		/**
		 * Object 转String
		 * @param param
		 * @return
		 */
		public static String objToString(Object param) {
			String result = "";
			BigDecimal re = new BigDecimal(0);
			DecimalFormat df = new DecimalFormat("#.00");
			if (param instanceof Integer) {
				int value = ((Integer) param).intValue();
				result = String.valueOf(value);
			} else if (param instanceof String) {
				result = (String) param;
			} else if (param instanceof Double) {
				double d = ((Double) param).doubleValue();
				result = String.valueOf(d);
			} else if (param instanceof Float) {
				float f = ((Float) param).floatValue();
				result = String.valueOf(f);
			} else if (param instanceof Long) {
				long l = ((Long) param).longValue();
				result = String.valueOf(l);
			} else if (param instanceof Boolean) {
				boolean b = ((Boolean) param).booleanValue();
				result = String.valueOf(b);
			} else if (param instanceof Date) {
				Date d = (Date) param;
				result = DateUtils.format(d);
			} else if( param instanceof BigDecimal ) {
				re = (BigDecimal) param;
				result= df.format(re);
			} else if( param instanceof BigInteger ) {
				re = new BigDecimal( (BigInteger) param );
				result= df.format(re);
			} else if( param instanceof Number ) {
				re = new BigDecimal( ((Number)param).doubleValue() );
				result= df.format(re);
			}
			return result;
		}

	/**
	 * Object 转 Object
	 * @param param
	 * @return
	 */
	public static Object objToObject(Object param) {
		Object result = "";
		BigDecimal re = new BigDecimal(0);
		if (param instanceof Integer) {
			result = (Integer) param;
		} else if (param instanceof String) {
			result = (String) param;
		} else if (param instanceof Double) {
			result = (Double) param;
		} else if (param instanceof Float) {
			result = (Float) param;
		} else if (param instanceof Long) {
			result = (Long) param;
		} else if (param instanceof Boolean) {
			result = (Boolean) param;
		} else if (param instanceof Date) {
			Date d = (Date) param;
			result = d;
		} else if( param instanceof BigDecimal ) {
			re = (BigDecimal) param;
			result= re;
		} else if( param instanceof BigInteger ) {
			re = new BigDecimal( (BigInteger) param );
			result= re;
		} else if( param instanceof Number ) {
			re = new BigDecimal( ((Number)param).doubleValue() );
			result= re;
		}
		return result;
	}

}
