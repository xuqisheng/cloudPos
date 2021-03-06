package com.greencloud.listener;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;

import com.greencloud.dao.ICacheDao;
import com.greencloud.dao.ILogDao;
import com.greencloud.entity.LogInfo;
import com.greencloud.entity.LogInfoConfig;
import com.greencloud.entity.OperationInfo;
import com.greencloud.util.DateUtil;
import com.greencloud.util.HibernateUtil;
import com.greencloud.util.UserManager;

public class LogInfoPosListener implements PostUpdateEventListener {

	private static final long serialVersionUID = 7886917269298302201L;
	/**
	 * pos日志
	 */
	private ILogDao logDao;

   private ICacheDao cacheDao;
   

	public void setCacheDao(ICacheDao cacheDao) {
	this.cacheDao = cacheDao;
}

	public void setLogDao(ILogDao logDao) {
		this.logDao = logDao;
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {

		// 得到正在修改的实体名
		String entityName = event.getPersister().getEntityName();
		Long hotelGroupId = UserManager.getHotelGroupId();
		Long hotelId = UserManager.getHotelId();
        //查找出
		if(event.getEntity() instanceof OperationInfo){
			OperationInfo operationInfo = (OperationInfo) event.getEntity();
			hotelGroupId = operationInfo.getHotelGroupId();
			hotelId = operationInfo.getHotelId();
			//更新codeCache表里相对应实体
			cacheDao.checkCodeCahche(entityName, hotelGroupId, hotelId);
		}
	
		int lastIndex = entityName.lastIndexOf(".");
		entityName = entityName.substring(lastIndex + 1);
		//此处可改为hibernate mapping的方法得到表名，更加正确，因
		//我们的系统采用的就是这种方法，所以直接这样搞了,提高性能
		entityName = HibernateUtil.addUnderscores(entityName).toUpperCase();

		
		List<LogInfoConfig> configList = logDao.listLogInfoConfig(entityName,
				UserManager.getHotelId(), UserManager.getHotelGroupId());

		Object[] oldValues = event.getOldState();// 旧对象值数组

		if (oldValues == null) {// 旧值没有
			// 旧对象值数组
			oldValues = event.getPersister().getDatabaseSnapshot(event.getId(),
					event.getSession());
		}

		// 新属性
		String[] propertyNames = event.getPersister().getPropertyNames();
		Object[] newsValues = event.getState();
		
		Date now = new Date();
		String needTranslate;
		for (int i = 0; i < newsValues.length; i++) {
			// 如果新旧值不相同
			if ((newsValues[i] != null && !obj2String(newsValues[i]).equals(obj2String(oldValues[i])))
					|| (oldValues[i] != null && !obj2String(oldValues[i])
							.equals(obj2String(newsValues[i])))  ) {
				String columnName = HibernateUtil.addUnderscores(propertyNames[i]);
				for(int k =0;k<configList.size();k++){
					LogInfoConfig config = configList.get(k);
					
					if(columnName.toUpperCase().equals(config.getEntityColumn())){
						LogInfo log = new LogInfo();
						log.setDescript(config.getDescript());
						log.setDescriptEn(config.getDescriptEn());
						log.setColumnName(columnName);
						log.setCreateDatetime(now);
						log.setCreateUser(UserManager.getUserName());
						log.setEntityId(Long.parseLong(event.getId().toString()));
						log.setEntityName(entityName);
						log.setHotelGroupId(hotelGroupId);
						log.setHotelId(hotelId);
						log.setNewValue(obj2String(newsValues[i]));
						log.setOldValue(obj2String(oldValues[i]));
						log.setStationCode(UserManager.getWorkStationCode());
						needTranslate = "N";
						if(config.getSqlDefine() != null && config.getSqlDefine().trim().length() > 0){
							needTranslate = "Y";
						}
						log.setNeedTranslate(needTranslate);
						logDao.saveLogInfo(log);
						configList.remove(config);
					}
				}
			}
		}
	}



	private String obj2String(Object obj) {
		String ret = "";
		if (obj == null) return "";
		if (obj instanceof Date) {
			ret = DateUtil.getNewFormatDateString((Date) obj);
		}else if (obj instanceof byte[]) {
			ret = "it's byte data.";
		}else if(obj instanceof BigDecimal){
			ret = NumberFormat.getInstance().format(obj);
		}else{
			ret = String.valueOf(obj);
		}
		if(ret.length() > 1000){
			ret = ret.substring(0, 999);
		}
		return ret;
	}
}
