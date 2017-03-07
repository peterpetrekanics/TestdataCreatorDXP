//Tested, works on Liferay 6.2 EE SP14
package com.liferay.controller;

import com.liferay.model.UserHandlerModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ProcessAction;

//import org.osgi.service.component.annotations.Component;
//@Component(
//        immediate = true,
//        property = {
//                "com.liferay.portlet.add-default-resource=true",
//                "com.liferay.portlet.application-type=full-page-application",
//                "com.liferay.portlet.application-type=widget",
//                "com.liferay.portlet.display-category=category.sample",
//                "com.liferay.portlet.layout-cacheable=true",
//                "com.liferay.portlet.preferences-owned-by-group=true",
//                "com.liferay.portlet.private-request-attributes=false",
//                "com.liferay.portlet.private-session-attributes=false",
//                "com.liferay.portlet.render-weight=50",
//                "com.liferay.portlet.scopeable=true",
//                "com.liferay.portlet.use-default-template=true",
//                "javax.portlet.display-name=Hello Soy Portlet",
//                "javax.portlet.expiration-cache=0",
//                "javax.portlet.init-param.copy-request-parameters=false",
//                "javax.portlet.init-param.template-path=/",
//                "javax.portlet.init-param.view-template=View",
//                "javax.portlet.name=hello_soy_portlet",
//                "javax.portlet.resource-bundle=content.Language",
//                "javax.portlet.security-role-ref=guest,power-user,user",
//                "javax.portlet.supports.mime-type=text/html"
//        },
//        service = Portlet.class
//)

/**
 * Portlet implementation class TestdataCreator
 */
public class TestdataCreatorDXP extends MVCPortlet {
	static long companyId;
	static User adminUser;
	long adminUserId;
	PrintWriter writer;
	
@Override
public void init() throws PortletException {
	// TODO Auto-generated method stub
	super.init();
	// Not needed - this is probably triggered when I add the portlet to a page
}

@Override
public void render(RenderRequest arg0, RenderResponse arg1) throws IOException, PortletException {
	// TODO Auto-generated method stub
	super.render(arg0, arg1);
	System.out.println("this runs every time2");
	
	// Getting the value for companyId
			Company company = null;
			try {
				company = CompanyLocalServiceUtil.getCompanies().iterator().next();
			} catch (SystemException e) {
				e.printStackTrace();
			}
			companyId = company.getCompanyId();
			System.out.println("companyId " + companyId);

			// Getting the id of the admin user
			try {
				adminUser = UserLocalServiceUtil.getUserByEmailAddress(companyId, "test@liferay.com");
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
			adminUserId = adminUser.getUserId();
			System.out.println("adminUserId " + adminUserId);
}

//	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
//			PortletException {
//		System.out.println("processAction starts..");
//
//		System.out.println("processAction ends..");
//	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			PortletException {
		// super.serveResource(resourceRequest, resourceResponse);
		System.out.println("serveResource starts..");

		
		
		UserHandlerModel userHandler = new UserHandlerModel();

		// Retrieving the action's name that was initiated by the user
		String performAction = ParamUtil.get(resourceRequest, "portletAction", "");

		// Performing the action based on user input
		switch (performAction) {
		case "createUsers":
			String newUserName = ParamUtil.getString(resourceRequest, "newUserName");
			int newUserCount = ParamUtil.getInteger(resourceRequest, "newUserCount");
			System.out.println("new user count: " +newUserCount);
			System.out.println("new user name: " +newUserName);
			if(newUserCount>0) userHandler.createUser(companyId, adminUserId, newUserName, newUserCount);
			resourceResponse.setContentType("text/html");
	        writer = resourceResponse.getWriter();
	        writer.println("User creation finished");
			break;
			
		case "deleteUsers":
			userHandler.deleteNonAdminUsers(companyId);
			break;
			
		case "createUserGroups":
			String newUserGroupName = ParamUtil.getString(resourceRequest, "newUserGroupName");
			int newUserGroupCount = ParamUtil.getInteger(resourceRequest, "newUserGroupCount");
			if(newUserGroupCount>0) userHandler.createUserGroup(companyId, adminUserId, newUserGroupName, newUserGroupCount);
			resourceResponse.setContentType("text/html");
	        writer = resourceResponse.getWriter();
	        writer.println("Usergroup creation finished");
			break;

		case "assignUsersToUserGroups":
			int assignedUserCount = ParamUtil.getInteger(resourceRequest, "assignedUserCount");
			if(assignedUserCount>0)	userHandler.assignUsersToUserGroups(companyId, assignedUserCount);
			resourceResponse.setContentType("text/html");
	        writer = resourceResponse.getWriter();
	        writer.println("User assigning finished");
			break;
			
		case "deleteUserGroups":
			userHandler.deleteUserGroups(companyId);
			break;
			
		case "createSites":
			String newSiteName = ParamUtil.getString(resourceRequest, "newSiteName");
			int newSiteCount = ParamUtil.getInteger(resourceRequest, "newSiteCount");
			if(newSiteCount>0) userHandler.createSite(companyId, adminUserId, newSiteName, newSiteCount);
			resourceResponse.setContentType("text/html");
	        writer = resourceResponse.getWriter();
	        writer.println("Site creation finished");
			break;
					
		default:
			;
			break;
		}
		System.out.println("The following button was pressed: " + performAction);
		
		System.out.println("serveResource ends..");
	}

	@ProcessAction(name = "userCreatorURL")
	public void userCreatorURL(ActionRequest actionRequest, ActionResponse actionResponse) {
		System.out.println("companyId2 " + companyId);
		String newUserName = ParamUtil.getString(actionRequest, "newUserName");
		int newUserCount = ParamUtil.getInteger(actionRequest, "newUserCount");
		System.out.println("new user count: " +newUserCount);
		System.out.println("new user name: " +newUserName);
		UserHandlerModel userHandler = new UserHandlerModel();
		if(newUserCount>0) userHandler.createUser(companyId, adminUserId, newUserName, newUserCount);
//		resourceResponse.setContentType("text/html");
//        writer = resourceResponse.getWriter();
//        writer.println("User creation finished");
	}
}
