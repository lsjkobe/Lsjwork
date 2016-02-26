package com.example.lsj.test;

import com.example.lsj.httplibrary.callback.ApiResponse;

import java.util.List;

public class TypeBean extends ApiResponse {
	private List<Type> list;

	public List<Type> getList() {
		return list;
	}

	public void setList(List<Type> list) {
		this.list = list;
	}

	public static class Type {
		private int type_id;
		private String picture;
		private String typename;

		public int getType_id() {
			return type_id;
		}

		public void setType_id(int type_id) {
			this.type_id = type_id;
		}

		public String getPicture() {
			return picture;
		}

		public void setPicture(String picture) {
			this.picture = picture;
		}

		public String getTypename() {
			return typename;
		}

		public void setTypename(String typename) {
			this.typename = typename;
		}

	}
}
