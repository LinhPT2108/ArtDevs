package com.art.dto.product;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
	private int id;
	private int star;
	private String content;
	private Date date;
	private String fullnameOfUser;
}