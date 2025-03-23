package service;

import java.util.List;

import model.Opentalk;

public interface OpentalkService {
	public List<Opentalk> getAll();
	public boolean add(Opentalk opentalk);
	public boolean delete(int code);
	public Opentalk search(int code);
	public Opentalk update(Opentalk opentalk);
}
