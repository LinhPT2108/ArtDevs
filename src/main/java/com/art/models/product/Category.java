package com.art.models.product;

import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.Nationalized;

import com.art.models.user.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor	
@AllArgsConstructor
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;

	@Column
	@Nationalized
	@NotNull(message = "Tên loại sản phẩm không được để trống")
	private String categoryName;

	@Column
	private boolean del;

	@ManyToOne
	@JoinColumn(name = "userCategory")
	private Account user;

	@OneToMany(mappedBy = "categoryProduct")
	private List<Product> category;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(categoryId, other.categoryId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId);
	}
	
	
}
