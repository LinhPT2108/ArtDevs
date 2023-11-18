package com.art.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.user.Account;

public interface UserCustomDAO extends JpaRepository<Account, String> {

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
//	UserCustom findByFullname(String fullname);
//
	// Tìm kiếm người dùng dựa trên trạng thái is_del
//	List<Account> findByDel(boolean _del);
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

}
