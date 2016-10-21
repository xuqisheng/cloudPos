package com.greencloud.service;
import com.aua.service.IBaseService;
import com.aua.util.Container;
import java.util.List;

import com.greencloud.entity.PosStorePluArticle;
import org.springframework.dao.DataAccessException;
public interface IPosStorePluArticleService extends IBaseService
{
   /**
   *save posStorePluArticle object  method
   *@param posStorePluArticle PosStorePluArticle 
   *@throws DataAccessException 
   *@author  
   *@version 1.0
   *@date 2016-01-21 09:34
   */
  public void savePosStorePluArticle(PosStorePluArticle posStorePluArticle) throws DataAccessException;
  
   /**
   *update posStorePluArticle method
   *@param posStorePluArticle PosStorePluArticle
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2016-01-21 09:34
   */
  public void updatePosStorePluArticle(PosStorePluArticle posStorePluArticle) throws DataAccessException;
  
   /**
   *save or update posStorePluArticle object method
   *@param posStorePluArticle PosStorePluArticle
   *@throws DataAccessException
   *@author  
   *@version 1.0
   *@date 2016-01-21 09:34
   */
  public void saveOrUpdatePosStorePluArticle(PosStorePluArticle posStorePluArticle) throws DataAccessException;

  /**
   *query a container obj
   *@param posStorePluArticle PosStorePluArticle query vo
   *@param firstResult
   *@param maxResults 
   *@throws DataAccessException 
   *@author weihuawon
   *@date 2016-01-21 09:34
   */
  public Container<PosStorePluArticle> findPosStorePluArticle(PosStorePluArticle posStorePluArticle,int firstResult ,int maxResults)throws DataAccessException;
   
   /**
   *query posStorePluArticle collection method
   *@param posStorePluArticle PosStorePluArticle send query vo 
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2016-01-21  09:34
   */
  public List<PosStorePluArticle> listPosStorePluArticle(PosStorePluArticle posStorePluArticle)throws DataAccessException;
 
   /**
   *query posStorePluArticle collection method
   *@param posStorePluArticle PosStorePluArticle send query vo 
   *@param firstResult 
   *@param maxResults 
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2016-01-21  09:34
   */
  public List<PosStorePluArticle> listPosStorePluArticle(PosStorePluArticle posStorePluArticle,int firstResult ,int maxResults)throws DataAccessException;
 
  /**
   *count obj amount
   *@param posStorePluArticle PosStorePluArticle query vo
   *@throws DataAccessException
   *@author 
   *@date 2016-01-21 09:34
   */
  public int countPosStorePluArticle(PosStorePluArticle posStorePluArticle)throws DataAccessException;
   
   /**
   *query a posStorePluArticle by id 
   *@param id 
   *@throws DataAccessException 
   *@author 
   *@date 2016-01-21 09:34
   */
  public PosStorePluArticle findPosStorePluArticleById(Long id)throws DataAccessException;
  
  
  /**
  *query a posStorePluArticle by id 
  *@param id 
  *@throws DataAccessException 
  *@author 
  *@date 2016-02-19 09:34
  */
 public void deletePluArticle(PosStorePluArticle posStorePluArticle);
}