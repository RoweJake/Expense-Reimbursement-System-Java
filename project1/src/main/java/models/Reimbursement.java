package models;

/**
 * The Reimbursement class represents reimbursement requests submitted to the ERS system.
 * 
 * @author Jake
 *
 */
public class Reimbursement {
	
	private int id;
	private int amount;
	private String submitDate;
	private String resolvedDate;
	private String description;
	private User author;
	private User resolver;
	private String status;
	private String type;
	
	// CONSTRUCTORS
	public Reimbursement() {
	}
	
	public Reimbursement(int amount, String submitDate, String description, User author, String status, String type) {
		this.amount = amount;
		this.submitDate = submitDate;
		this.description = description;
		this.author = author;
		this.status = status;
		this.type = type;
	}

	public Reimbursement(int id, int amount, String submitDate, String resolvedDate, String description, User author,
			User resolver, String status, String type) {
		this.id = id;
		this.amount = amount;
		this.submitDate = submitDate;
		this.resolvedDate = resolvedDate;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	// GETTERS
	public int getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public String getResolvedDate() {
		return resolvedDate;
	}

	public String getDescription() {
		return description;
	}

	public User getAuthor() {
		return author;
	}

	public User getResolver() {
		return resolver;
	}

	public String getStatus() {
		return status;
	}

	public String getType() {
		return type;
	}

	// SETTERS
	public void setId(int id) {
		this.id = id;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public void setResolvedDate(String resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void setResolver(User resolver) {
		this.resolver = resolver;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	// toString OVERRIDE
	//TODO: Possibly update depending on needs
	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submitDate=" + submitDate + ", resolvedDate="
				+ resolvedDate + ", description=" + description + ", author=" + author + ", resolver=" + resolver
				+ ", status=" + status + ", type=" + type + "]";
	}

}