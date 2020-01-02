package com.ats.hreasy.common;

import java.util.List;

import com.ats.hreasy.model.AccessRightModule;
import com.ats.hreasy.model.Info;

 

public class AcessController {
	

	public static Info checkAccess(String currentMapping, String viewMapping, int view, int add, int edit,
			int delete, List<AccessRightModule> newModuleList) {

		Info info = new Info();

		try {

			//System.out.println("in function newModuleList " + newModuleList);

			int viewMappingFound = 0;

			for (int i = 0; i < newModuleList.size(); i++) {

				for (int j = 0; j < newModuleList.get(i).getAccessRightSubModuleList().size(); j++) {

					if (newModuleList.get(i).getAccessRightSubModuleList().get(j).getSubModuleMapping().equals(viewMapping)) {
						viewMappingFound = 1;

						//System.out.println("Mapping Found");

						if (view == 1) {

							//System.out.println("in view");
							info.setError(false);
							info.setMsg("Have Access");
							
						} else if (add == 1) {
							
							//System.out.println("in add");
							
							if (newModuleList.get(i).getAccessRightSubModuleList().get(j).getAddApproveConfig()== add) {
								//System.out.println("in add if");
								info.setError(false);
								info.setMsg("Have Access");
							} else {
								//System.out.println("in add else");
								info.setError(true);
								info.setMsg("Access denied");
							}
						} else if (edit == 1) {

							//System.out.println("in edit");
							if (newModuleList.get(i).getAccessRightSubModuleList().get(j).getEditReject() == edit) {
								//System.out.println("in edit if");
								info.setError(false);
								info.setMsg("Have Access");
							} else {
								//System.out.println("in edit else");
								info.setError(true);
								info.setMsg("Access denied");
							}
						} else if (delete == 1) {
							//System.out.println("in delete");
							if (newModuleList.get(i).getAccessRightSubModuleList().get(j).getDeleteRejectApprove()
									== (delete)) {
								//System.out.println("in edit if");
								info.setError(false);
								info.setMsg("Have Access");
							} else {
								//System.out.println("in edit else");
								info.setError(true);
								info.setMsg("Access denied");
							}
						} else {
							//System.out.println("in else");
							info.setError(true);
							info.setMsg("Access denied");
						}

					}
				}

			}

			if (viewMappingFound == 0) {
				//System.out.println("mapping not found");
				info.setError(true);
				info.setMsg("Access Denied");
			}

		} catch (Exception e) {
			e.printStackTrace();
			info.setError(true);
			info.setMsg("access denied ");
		}

		return info;

	}

}
