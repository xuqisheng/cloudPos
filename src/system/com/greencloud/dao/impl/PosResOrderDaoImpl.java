package com.greencloud.dao.impl;
import com.aua.util.SQLHelper;
import com.aua.dao.impl.BaseDaoImpl;

import static com.aua.util.StringHelper.*;

import java.util.List;

import com.greencloud.entity.OperationInfo;
import com.greencloud.entity.PosResOrder;
import com.greencloud.dao.IPosResOrderDao;

import org.springframework.dao.DataAccessException;

   /**
   * operate PosResOrder into db
   *@author 
   *@version 1.0
   *@date 2015-04-01 13:44
   */
  public class PosResOrderDaoImpl extends BaseDaoImpl implements IPosResOrderDao{

  /**
   *save posResOrder object  method
   *@param posResOrder PosResOrder 
   *@throws DataAccessException 
   *@author  
   *@version 1.0
   *@date 2015-04-01 13:44
   */
  public void save(PosResOrder posResOrder) throws DataAccessException {
       super.save(posResOrder);
  }
  
  /**
   *update posResOrder method
   *@param posResOrder PosResOrder
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2015-04-01 13:44
   */
  public void update(PosResOrder posResOrder) throws DataAccessException{
     super.update(posResOrder);
  }
  
   /**
   *save or update posResOrder object method
   *@param posResOrder PosResOrder
   *@throws DataAccessException
   *@author  
   *@version 1.0
   *@date 2015-04-01 13:44
   */
  public void saveOrUpdate(PosResOrder posResOrder) throws DataAccessException{
     super.saveOrUpdate(posResOrder);
  }
  
   /**
   *query posResOrder collection method
   *@param posResOrder PosResOrder send query vo 
   *@param firstResult 
   *@param maxResults 
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2015-04-01  13:44
   */
  public List<PosResOrder> list(PosResOrder posResOrder,int firstResult ,int maxResults)throws DataAccessException{
  	 SQLHelper sh = new SQLHelper("SELECT obj FROM PosResOrder obj WHERE 1=1 ");
  	 spellSQL(sh,posResOrder);
  	 sh.appendSql(" ORDER BY obj.id DESC");
  	 sh.setFirstResult(firstResult);
  	 sh.setMaxResults(maxResults);
  	 return find(sh);
  }
  
   /**
   *query collection method  
   *@param posResOrder PosResOrder send query vo 
   *@throws DataAccessException
   *@author 
   *@version 1.0
   *@date 2015-04-01 13:44
   */
  public List<PosResOrder> list(PosResOrder posResOrder)throws DataAccessException{
	     SQLHelper sh = new SQLHelper("SELECT obj FROM PosResOrder obj WHERE 1=1 ");
	  	 spellSQL(sh,posResOrder);
	  	 sh.appendSql(" ORDER BY obj.id");
	  	 return find(sh);
  }
  
  /**
   *count obj amount
   *@param posResOrder PosResOrder query vo
   *@throws DataAccessException
   *@author 
   *@date 2015-04-01 13:44
   */
  public int count(PosResOrder posResOrder)throws DataAccessException
  {
    SQLHelper sh = new SQLHelper("SELECT count(obj) FROM PosResOrder obj WHERE 1=1 ");
    spellSQL(sh,posResOrder);
    return count(sh);
  }
  
   /**
   *query a posResOrder by id 
   *@param id 
   *@throws DataAccessException 
   *@author 
   *@date 2015-04-01 13:44
   */
  public PosResOrder load(Long id)throws DataAccessException
  {
    return load(PosResOrder.class ,id);
  }
  
  /*
   *basic spell SQL method 
   *@param posResOrder query vo
   *@throws DataAccessException 
   *@author weihuawon
   *@date 2015-04-01 13:44
   */
  private void spellSQL(SQLHelper sh,PosResOrder posResOrder)
  {
    if(posResOrder != null){
    	if(posResOrder.getId()!= null){
            sh.appendSql(" AND obj.id = ? ");
            sh.insertValue(posResOrder.getId());
         }
         if(posResOrder.getHotelGroupId()!= null){
            sh.appendSql(" AND obj.hotelGroupId = ? ");
            sh.insertValue(posResOrder.getHotelGroupId());
         }
         if(posResOrder.getHotelId()!= null){
            sh.appendSql(" AND obj.hotelId = ? ");
            sh.insertValue(posResOrder.getHotelId());
         }
        if(isNotNull(posResOrder.getPcid())){
           sh.appendSql(" AND obj.pcid = ? ");
           sh.insertValue(posResOrder.getPcid().trim());
        }
        if(isNotNull(posResOrder.getAccnt())){
           sh.appendSql(" AND obj.accnt = ? ");
           sh.insertValue(posResOrder.getAccnt().trim());
        }
       if(posResOrder.getInumber()!= null){
           sh.appendSql(" AND obj.inumber = ? ");
           sh.insertValue(posResOrder.getInumber());
        }
       if(posResOrder.getTnumber()!= null){
           sh.appendSql(" AND obj.tnumber = ? ");
           sh.insertValue(posResOrder.getTnumber());
        }
       if(posResOrder.getMnumber()!= null){
           sh.appendSql(" AND obj.mnumber = ? ");
           sh.insertValue(posResOrder.getMnumber());
        }
        if(isNotNull(posResOrder.getType())){
           sh.appendSql(" AND obj.type = ? ");
           sh.insertValue(posResOrder.getType().trim());
        }
        if(isNotNull(posResOrder.getOrderno())){
           sh.appendSql(" AND obj.orderno = ? ");
           sh.insertValue(posResOrder.getOrderno().trim());
        }
      
       if(isNotNull(posResOrder.getCondCode())){
           sh.appendSql(" AND obj.condCode = ? ");
           sh.insertValue(posResOrder.getCondCode());
        }
        if(isNotNull(posResOrder.getDescript())){
           sh.appendSql(" AND obj.descript = ? ");
           sh.insertValue(posResOrder.getDescript().trim());
        }
        if(isNotNull(posResOrder.getDescriptEn())){
           sh.appendSql(" AND obj.descriptEn = ? ");
           sh.insertValue(posResOrder.getDescriptEn().trim());
        }
        if(isNotNull(posResOrder.getNoteCode())){
           sh.appendSql(" AND obj.noteCode = ? ");
           sh.insertValue(posResOrder.getNoteCode().trim());
        }
        if(isNotNull(posResOrder.getSortCode())){
           sh.appendSql(" AND obj.sortCode = ? ");
           sh.insertValue(posResOrder.getSortCode().trim());
        }
        if(isNotNull(posResOrder.getPluCode())){
           sh.appendSql(" AND obj.pluCode = ? ");
           sh.insertValue(posResOrder.getPluCode().trim());
        }
       if(posResOrder.getPinumber()!= null){
           sh.appendSql(" AND obj.pinumber = ? ");
           sh.insertValue(posResOrder.getPinumber());
        }
        if(isNotNull(posResOrder.getUnit())){
           sh.appendSql(" AND obj.unit = ? ");
           sh.insertValue(posResOrder.getUnit().trim());
        }
       if(posResOrder.getPrice()!= null){
           sh.appendSql(" AND obj.price = ? ");
           sh.insertValue(posResOrder.getPrice());
        }
       if(posResOrder.getNumber()!= null){
           sh.appendSql(" AND obj.number = ? ");
           sh.insertValue(posResOrder.getNumber());
        }
       if(posResOrder.getAmount()!= null){
           sh.appendSql(" AND obj.amount = ? ");
           sh.insertValue(posResOrder.getAmount());
        }
        if(isNotNull(posResOrder.getSta())){
           sh.appendSql(" AND obj.sta = ? ");
           sh.insertValue(posResOrder.getSta().trim());
        }
        if(isNotNull(posResOrder.getFlag())){
           sh.appendSql(" AND obj.flag = ? ");
           sh.insertValue(posResOrder.getFlag().trim());
        }
        if(isNotNull(posResOrder.getFlag1())){
           sh.appendSql(" AND obj.flag1 = ? ");
           sh.insertValue(posResOrder.getFlag1().trim());
        }
        if(isNotNull(posResOrder.getEmpid())){
           sh.appendSql(" AND obj.empid = ? ");
           sh.insertValue(posResOrder.getEmpid().trim());
        }
       if(posResOrder.getLogdate()!= null){
           sh.appendSql(" AND obj.logdate = ? ");
           sh.insertValue(posResOrder.getLogdate());
        }
        if(isNotNull(posResOrder.getSaleid())){
           sh.appendSql(" AND obj.saleid = ? ");
           sh.insertValue(posResOrder.getSaleid().trim());
        }
        if(isNotNull(posResOrder.getTableno())){
           sh.appendSql(" AND obj.tableno = ? ");
           sh.insertValue(posResOrder.getTableno().trim());
        }
        if(isNotNull(posResOrder.getSiteno())){
           sh.appendSql(" AND obj.siteno = ? ");
           sh.insertValue(posResOrder.getSiteno().trim());
        }
        if(isNotNull(posResOrder.getCook())){
           sh.appendSql(" AND obj.cook = ? ");
           sh.insertValue(posResOrder.getCook().trim());
        }
        if(isNotNull(posResOrder.getRemark())){
           sh.appendSql(" AND obj.remark = ? ");
           sh.insertValue(posResOrder.getRemark().trim());
        }
        if(isNotNull(posResOrder.getPrinter())){
           sh.appendSql(" AND obj.printer = ? ");
           sh.insertValue(posResOrder.getPrinter().trim());
        }
        if(isNotNull(posResOrder.getEmpid1())){
           sh.appendSql(" AND obj.empid1 = ? ");
           sh.insertValue(posResOrder.getEmpid1().trim());
        }
        if(isNotNull(posResOrder.getEmpid2())){
           sh.appendSql(" AND obj.empid2 = ? ");
           sh.insertValue(posResOrder.getEmpid2().trim());
        }
        if(isNotNull(posResOrder.getEmpid3())){
           sh.appendSql(" AND obj.empid3 = ? ");
           sh.insertValue(posResOrder.getEmpid3().trim());
        }
        if(isNotNull(posResOrder.getCreateUser())){
            sh.appendSql(" AND obj.createUser = ? ");
            sh.insertValue(posResOrder.getCreateUser().trim());
         }
        if(posResOrder.getCreateDatetime()!= null){
            sh.appendSql(" AND obj.createDatetime = ? ");
            sh.insertValue(posResOrder.getCreateDatetime());
         }
         if(isNotNull(posResOrder.getModifyUser())){
            sh.appendSql(" AND obj.modifyUser = ? ");
            sh.insertValue(posResOrder.getModifyUser().trim());
         }
        if(posResOrder.getModifyDatetime()!= null){
            sh.appendSql(" AND obj.modifyDatetime = ? ");
            sh.insertValue(posResOrder.getModifyDatetime());
         }
    }
  }
}