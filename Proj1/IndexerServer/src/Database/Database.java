package Database;

import DAO.BatchDAO;
import DAO.FieldDAO;
import DAO.ProjectDAO;
import DAO.RecordDAO;
import DAO.UserDAO;

/**
 * Database Class
 * 
 * Contains one instance of each DAO
 * This class directly handles the information in the database
 * Using each one of the DAO's can add information or retrieve it from the database
 * 
 * @author aconstan
 *
 */

public class Database {
	
	private UserDAO udao;
	private ProjectDAO pdao;
	private BatchDAO bdao;
	private FieldDAO fdao;
	private RecordDAO rdao;
	
	public Database()
	{
		udao = new UserDAO();
		pdao = new ProjectDAO();
		bdao = new BatchDAO();
		fdao = new FieldDAO();
		rdao = new RecordDAO();
	}

	public UserDAO getUdao() {
		return udao;
	}

	public void setUdao(UserDAO udao) {
		this.udao = udao;
	}

	public ProjectDAO getPdao() {
		return pdao;
	}

	public void setPdao(ProjectDAO pdao) {
		this.pdao = pdao;
	}

	public BatchDAO getBdao() {
		return bdao;
	}

	public void setBdao(BatchDAO bdao) {
		this.bdao = bdao;
	}

	public FieldDAO getFdao() {
		return fdao;
	}

	public void setFdao(FieldDAO fdao) {
		this.fdao = fdao;
	}

	public RecordDAO getRdao() {
		return rdao;
	}

	public void setRdao(RecordDAO rdao) {
		this.rdao = rdao;
	}

}
