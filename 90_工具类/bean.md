bean

commons-beanutils

bean复制
SampleBean bean3 = new SampleBean();  
bean3.setName("rensanning");  
bean3.setAge(31);  
  
SampleBean clone = (SampleBean) BeanUtils.cloneBean(bean3);  
  
System.out.println(clone.getName());  
System.out.println(clone.getAge());  

bean转map
describe 
SampleBean bean4 = new SampleBean();  
bean4.setName("rensanning");  
bean4.setAge(31);  
  
@SuppressWarnings("unchecked")  
Map<String, String> map4 = BeanUtils.describe(bean4);  
  
System.out.println(map4.get("name"));  
System.out.println(map4.get("age")); 

populate 

SampleBean bean5 = new SampleBean();  
  
Map<String, String> map5 = new HashMap<String, String>();  
map5.put("name", "rensanning");  
map5.put("age", "31");  
  
BeanUtils.populate(bean5, map5);  
  
System.out.println(bean5.getName());  
System.out.println(bean5.getAge());  
