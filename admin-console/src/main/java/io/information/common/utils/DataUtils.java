package io.information.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		
		
}
