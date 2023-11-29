package com.art.dao.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.art.models.user.Account;
import com.art.models.user.AccountRole;
import com.art.models.user.Role;

public interface AccountDAO extends JpaRepository<Account, String> {

//	// Thêm hoặc cập nhật một người dùng
//	@SuppressWarnings("unchecked")
//	UserCustom save(UserCustom user);
//
//	// Xóa một người dùng dựa trên ID
//	void deleteById(String userId);
//
//	// Kiểm tra xem một người dùng có tồn tại dựa trên ID hay không
//	boolean existsById(String userId);
//
//	// Tìm kiếm người dùng dựa trên họ tên
//	Account findByFullname(String fullname);
//
	// Tìm kiếm người dùng dựa trên trạng thái is_del
	List<Account> findByStatus(boolean del);
//
//	// Tìm kiếm người dùng dựa trên vai trò
//	List<UserCustom> findByRole(Role role);
//
	// Tìm kiếm người dùng dựa trên email
//	List<Account> findByEmail(String email);
//
//	@Query("SELECT u.accountId, u.fullname, u.email, COUNT(i.id) " + "FROM Account u " + "LEFT JOIN u.userInvoice i "
//			+ "GROUP BY u.accountId, u.fullname, u.email")
//	List<Object[]> getUsersWithInvoiceCount();
//
//	// Tìm kiếm người dùng dựa trên họ tên chứa một từ khóa
//	List<UserCustom> findByFullnameContainingIgnoreCase(String keyword);
	
	Optional<Account> findByEmail(String email);
	
//	@Query("SELECT a.userRole.role From Account a WHERE a.email =:email")
//	List<Role> getRoles(String email);
	
	@Query("SELECT a.userRole From Account a WHERE a.email =:email")
	List<AccountRole> getAccountRoles(String email);
}
