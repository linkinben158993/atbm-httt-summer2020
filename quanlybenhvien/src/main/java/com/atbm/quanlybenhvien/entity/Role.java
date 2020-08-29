package com.atbm.quanlybenhvien.entity;

import java.util.ArrayList;
import java.util.List;

public class Role {
	public static final List<String> ROLE_LIST = assignRole();

	public static List<String> assignRole() {
		List<String> roleList = new ArrayList<String>();

		roleList.add("DBA");
		roleList.add("ROLE_BACSI");
		roleList.add("ROLE_NVBANTHUOC");
		roleList.add("ROLE_NVKETOAN");
		roleList.add("ROLE_NVTAIVU");
		roleList.add("ROLE_NVTIEPTANDIEUPHOI");
		roleList.add("ROLE_QLTAIVU");
		roleList.add("ROLE_QLTAINGUYENNHANSU");
		roleList.add("ROLE_QLCHUYENMON");

		return roleList;
	}
}
