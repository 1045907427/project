package com.hd.agent.activiti.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.activiti.model.Comment;
import com.hd.agent.activiti.model.Viewer;

public interface CommentMapper {
	
	public int addComment(Comment comment);
	
	public int updateComment(Comment comment);

	/**
	 * 根据taskid获取comment
	 * @param id
	 * @return
	 * @author limin
	 * @date Jun 14, 2017
	 */
	public Comment getCommentByTask(String id);

	/**
	 * 根据taskid获取comment
	 * @param userid
	 * @param taskid
	 * @return
	 * @author limin
	 * @date Jun 14, 2017
	 */
	public Comment getCommentByUserTask(String userid, String taskid);
	
	public List getCommentListByInstance(String id);
	
	public Comment getCommentByUser(String instanceid, String userId);
	
	public Viewer getViewerByTask(String taskid);
	
	public List<Viewer> getViewerByInstance(String id);
	
	public int addViewer(Viewer viewer);
	
	public int updateViewer(Viewer viewer);
	
	public int deleteComment(String id);

	public List getCommentListByProcessid(String id);
	
	public Comment getComment(@Param("id")String id);
	
	public List<Comment> getComments(Map map);

    /**
     * 根据processid删除审批信息
     * @param processid
     * @return
     * @author limin
     * @date Feb 27, 2015
     */
    public int deleteCommentByProcessid(String processid);
}