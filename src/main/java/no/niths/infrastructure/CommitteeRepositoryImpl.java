package no.niths.infrastructure;

import java.util.List;

import no.niths.domain.Committee;
import no.niths.infrastructure.interfaces.CommitteesRepository;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CommitteeRepositoryImpl implements CommitteesRepository {

	@Autowired
	private SessionFactory session;
	
	
	@Transactional(readOnly = false)
	public Long create(Committee domain) {
		return (Long) session.getCurrentSession().save(domain);
	}

	@SuppressWarnings("unchecked")
	public List<Committee> getAllCommittees() {
		return  session.getCurrentSession().createQuery("from " + Committee.class.getName())
				.list();
	}

	public Committee getCommitteeById(long cid) {
		return (Committee) session.getCurrentSession().get(Committee.class, cid);
	}

	@Transactional(readOnly = false)
	public void update(Committee domain) {
		session.getCurrentSession().update(domain);
	}

	@Transactional(readOnly = false)
	public boolean delete(long id) {
	    Query query = session.getCurrentSession().createQuery(
        		"delete " + Committee.class.getSimpleName() + " where id = :id");
        query.setParameter("id", id);
        
        return (1 == query.executeUpdate());
	}

	@Override
	public Committee getCommitteeByName(String name) {
		String sql ="from " + Committee.class.getName() + " c where c.name=:name";
		return (Committee) session.getCurrentSession().createQuery(sql).setString("name",name).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Committee> getAllCommittees(Committee committee) {
		return  session.getCurrentSession().createCriteria(Committee.class).add(Example.create(committee)).list();
		
	}

}
