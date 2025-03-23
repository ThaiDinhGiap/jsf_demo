package service.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import dao.OpentalkDAO;
import model.Opentalk;
import service.OpentalkService;

@ApplicationScoped
public class OpentalkServiceImpl implements OpentalkService{
	
	@Inject
	private OpentalkDAO opentalkDAO;
	
	@Override
	public List<Opentalk> getAll() {
		// TODO Auto-generated method stub
		return opentalkDAO.getAllOpentalks();
	}

	@Override
	public boolean add(Opentalk opentalk) {
		// TODO Auto-generated method stub
		return opentalkDAO.addOpentalk(opentalk);
	}

	@Override
	public boolean delete(int code) {
		// TODO Auto-generated method stub
		return opentalkDAO.deleteOpentalk(code);
	}

	@Override
	public Opentalk search(int code) {
		// TODO Auto-generated method stub
		return opentalkDAO.searchOpentalk(code);
	}

	@Override
	public Opentalk update(Opentalk opentalk) {
		// TODO Auto-generated method stub
		return opentalkDAO.updateOpentalk(opentalk);
	}

}
