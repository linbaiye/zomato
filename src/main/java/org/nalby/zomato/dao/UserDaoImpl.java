package org.nalby.zomato.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nalby.zomato.entity.User;
import org.nalby.zomato.util.QueryName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<User> getUsersByIds(List<Long> ids) {
		Session session = sessionFactory.getCurrentSession();
		Query query = (Query) session.getNamedQuery(QueryName.GET_USERS_BY_IDS);
		query.setParameterList("ids", ids);
		return query.list();
	}

	public User loadUserByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = (Query) session.getNamedQuery(QueryName.GET_USER_BY_NAME);
		query.setParameter("name", name);
		return (User) query.uniqueResult();
	}
}
