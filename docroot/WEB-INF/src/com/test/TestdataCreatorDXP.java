package com.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class TestdataCreatorDXP
 */
public class TestdataCreatorDXP extends MVCPortlet {
	
	static long companyId;
	static User adminUser;
	long adminUserId;
	PrintWriter writer;
 
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
		System.out.println("ProcessAction starts..");
		
		
		final Company company = CompanyLocalServiceUtil.getCompanies().iterator().next();

        long num = System.currentTimeMillis();

        for (int i = 0; i < 81; i++) {
            try {
                final ServiceContext serviceContext = null;
                UserLocalServiceUtil.addUser(
                        0, //creatorUserId,
                        company.getCompanyId(), //companyId,
                        false, // autoPassword,
                        "test", //password1,
                        "test", //password2,
                        true, //autoScreenName,
                        null, //screenName,
                        "test" + i + "@liferay.com", //emailAddress,
                        0L, //facebookId,
                        null, //openId,
                        Locale.US, //locale,
                        "Test", //firstName,
                        null, //middleName,
                        "User" + i, //lastName,
                        0, //prefixId,
                        0, //suffixId,
                        true, //male,
                        1, //birthdayMonth,
                        1, //birthdayDay,
                        1977, //birthdayYear,
                        null, //jobTitle,
                        null, //groupIds,
                        null, //organizationIds,
                        null, //roleIds,
                        null, //userGroupIds,
                        false, //sendEmail,
                        serviceContext);
                System.out.println("User number: " + i + " has been created");
            } catch (final Exception e) {
                System.out.println("Error in test when creating user #{}");
            }
        }
		
//		
//		args.setParameter("creatorUserId", .getUserId());
//		args.setParameter("password1", "test");
//		args.setParameter("password2", "test");
//		args.setParameter("screenName", screenName);
//		args.setParameter("emailAddress", screenName + "@liferay.com");
//		args.setParameter("facebookId", (long)0);
//		args.setParameter("openId", StringPool.BLANK);
//		args.setParameter("firstName", "Test" + postString);
//		args.setParameter("middleName", StringPool.BLANK);
//		args.setParameter("lastName", "Test" + postString);
//		args.setParameter("prefixId", 0);
//		args.setParameter("suffixId", 0);
//		args.setParameter("birthdayMonth", 10);
//		args.setParameter("birthdayDay", 16);
//		args.setParameter("birthdayYear", 1984);
//		args.setParameter("jobTitle", StringPool.BLANK);
//		args.setParameter("groupIds", requestProcessor.getGroupIds());
//
//		args.setParameter(
//			"organizationIds", requestProcessor.getOrganizationIds());
//
//		args.setParameter("roleIds", requestProcessor.getRoleIds());
//		args.setParameter("userGroupIds", requestProcessor.getUserGroupIds());
//		args.setParameter("sendEmail", false);
//
//		
//		
//		UserLocalServiceUtil.addUser(creatorUserId, companyId, autoPassword, password1, password2, autoScreenName, screenName, emailAddress, facebookId, openId, locale, firstName, middleName, lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds, userGroupIds, sendEmail, serviceContext)
		
		System.out.println("ProcessAction ends..");
	}

}
