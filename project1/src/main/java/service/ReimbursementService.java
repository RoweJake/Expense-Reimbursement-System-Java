package service;

import java.util.ArrayList;

import dao.ReimbursementDao;
import models.Reimbursement;
import models.User;

public class ReimbursementService {
	private ReimbursementDao reimbDao;
	
	public ReimbursementService(ReimbursementDao reimbDao) {
		this.reimbDao = reimbDao;
	}
	
	public ArrayList<Reimbursement> findAllReimb() {
		return reimbDao.readAllReimb();
	}
	
	public ArrayList<Reimbursement> findFilteredReimb(String filter) {
		return reimbDao.readFilteredReimb(filter);
	}
	
	public ArrayList<Reimbursement> findAccountReimb(User user) {
		int userId = user.getId();
		return reimbDao.readUserReimb(userId);
	}
	
	public boolean addReimb(Reimbursement newReimb) {
		return reimbDao.createReimb(newReimb);
	}
	
	public boolean resolveReimb(Reimbursement updatedReimb) {
		return reimbDao.updateReimbStatus(updatedReimb.getId(), updatedReimb.getResolvedDate(), updatedReimb.getResolver(), updatedReimb.getStatus());
	}
	
}