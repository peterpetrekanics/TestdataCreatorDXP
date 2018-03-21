package TestdataCreatorDXP.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserHandlerModel {

	private static final String String = null;


	public void createUser(long companyId, long adminUserId,
			String newUserName, int userCount) {
			ServiceContext serviceContext = null;
			
			System.out.println("testing1");
		for (int currentUserNumber = 1; currentUserNumber <= userCount; currentUserNumber++) {
			try {
				UserLocalServiceUtil.addUser(adminUserId, // creatorUserId,
						companyId, // companyId,
						false, // autoPassword,
						"test", // password1,
						"test", // password2,
						true, // autoScreenName,
						null, // screenName,
						newUserName + currentUserNumber + "@liferay.com", // emailAddress,
						0L, // facebookId,
						null, // openId,
						Locale.ENGLISH, // locale,
						"Test", // firstName,
						null, // middleName,
						newUserName + currentUserNumber, // lastName,
						0, // prefixId,
						0, // suffixId,
						true, // male,
						1, // birthdayMonth,
						1, // birthdayDay,
						1977, // birthdayYear,
						null, // jobTitle,
						null, // groupIds,
						null, // organizationIds,
						null, // roleIds,
						null, // userGroupIds,
						false, // sendEmail,
						serviceContext); // serviceContext
				System.out.println("The user: " + newUserName
						+ currentUserNumber + " has been created");

			} catch (Exception e) {
				System.out.println("exception" + e);
				e.printStackTrace();

			} finally {
				try {
					System.out.println("User count after user creation: "
							+ getUserCount());
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void deleteNonAdminUsers(long companyId) {
		List<User> myUsers = null;
		try {
			myUsers = UserLocalServiceUtil.getUsers(0, getUserCount());
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		for (User user : myUsers) {
			if (user.isDefaultUser()
					|| PortalUtil.isOmniadmin(user.getUserId())) {
				System.out.println("Skipping user " + user.getScreenName());
			} else {
				User userToDelete = user;
				System.out.println("Deleting user "
						+ userToDelete.getScreenName());
				try {
					UserLocalServiceUtil.deleteUser(userToDelete);
				} catch (PortalException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void createUserGroup(long companyId, long adminUserId,
			String newUserGroupName, int userGroupCount) {

		ServiceContext serviceContext = null;
		
		for (int currentUserGroupNumber = 1; currentUserGroupNumber <= userGroupCount; currentUserGroupNumber++) {
			try {
				UserGroupLocalServiceUtil.addUserGroup(adminUserId, companyId,
						newUserGroupName + String.format("%06d", currentUserGroupNumber),
						"description", serviceContext);
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			} finally {
				try {
					System.out
							.println("Usergroup count after usergroup creation: "
									+ getUserGroupCount());
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static int getUserCount() throws SystemException {
		return UserLocalServiceUtil.getUsersCount();
	}

	public static int getUserGroupCount() throws SystemException {
		return UserGroupLocalServiceUtil.getUserGroupsCount();
	}

	public void assignUsersToUserGroups(long companyId, int assignedUserCount) {
		try {
			List<User> myUsers = null;
			int currentUserGroupNumber = 0;
			int currentUserNumber = 1;
			myUsers = UserLocalServiceUtil.getUsers(0, getUserCount());
			
			for (User user : myUsers) {
				if (user.isDefaultUser()
						|| PortalUtil.isOmniadmin(user.getUserId())) {
					System.out.println("Skipping user " + user.getScreenName());
				} else {
					User userToAssign = user;
					List<UserGroup> userGroupList = UserGroupLocalServiceUtil
							.getUserGroups(0, getUserGroupCount());
					try {
//						System.out.println("curr usr nr: " + currentUserNumber);
//						System.out.println("curr usr grp nr: "	+ currentUserGroupNumber);
						if (currentUserNumber != 1) {
							if ((currentUserNumber - 1) % assignedUserCount == 0) {
								if (currentUserGroupNumber + 1 >= getUserGroupCount()) {
									currentUserGroupNumber = 0;
								} else {
									currentUserGroupNumber++;
								}
							}
						}
						UserGroupLocalServiceUtil.addUserUserGroup(
								userToAssign.getUserId(),
								userGroupList.get(currentUserGroupNumber));

						currentUserNumber++;

						System.out.println("The user: "
								+ userToAssign.getScreenName()
								+ " has been added to "
								+ userGroupList.get(currentUserGroupNumber)
										.getName());

					} catch (SystemException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (SystemException e) {
			e.printStackTrace();
		}
	}

	public void deleteUserGroups(long companyId) {
		List<UserGroup> myUserGroups = null;
		try {
			myUserGroups = UserGroupLocalServiceUtil.getUserGroups(0, getUserGroupCount());
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		for (UserGroup myUserGroup : myUserGroups) {
			System.out.println("Deleting userGroup: " + myUserGroup.getName());
			try {
				UserGroupLocalServiceUtil.deleteUserGroup(myUserGroup);
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int getSiteCount() throws SystemException {
		//Use -19 because there are 19 technical sites that we don't want to show in the count.
		return GroupLocalServiceUtil.getGroupsCount()-19;
	}

	
	public void createSite(long companyId, long adminUserId,
			String newSiteName, int newSiteCount) {

		ServiceContext serviceContext = new ServiceContext();
		
		System.out.println("count: "+newSiteCount);
		
		for (int currentSiteNumber = 1; currentSiteNumber <= newSiteCount; currentSiteNumber++) {
			try {
				
				//Add the group
//				Group group = GroupLocalServiceUtil.addGroup(adminUserId, GroupConstants.DEFAULT_PARENT_GROUP_ID, 
//						Group.class.getName(), 0, GroupConstants.DEFAULT_LIVE_GROUP_ID, newSiteName + currentSiteNumber, "", 
//						GroupConstants.TYPE_SITE_OPEN, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, 
//						"/" + newSiteName + currentSiteNumber, true, true, serviceContext);
				
//				Group group = GroupLocalServiceUtil.addGroup(adminUserId, GroupConstants.DEFAULT_PARENT_GROUP_ID, 
//						Group.class.getName(), 0, GroupConstants.DEFAULT_LIVE_GROUP_ID, newSiteName + currentSiteNumber, "", 
//						GroupConstants.TYPE_SITE_OPEN, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, 
//						"/" + newSiteName + currentSiteNumber, true, true, true, serviceContext);
				
				
				
//				addGroup(long userId, long parentGroupId, String className, long classPK, long liveGroupId, Map<Locale,
//						String> nameMap, Map<Locale,String> descriptionMap, int type, boolean manualMembership, 
//						int membershipRestriction, String friendlyURL, boolean site, boolean inheritContent, 
//						boolean active, ServiceContext serviceContext) 
				System.out.println("test1");
				
				Map<Locale, String> nameMap = new HashMap<>();
				nameMap.put(LocaleUtil.getDefault(), newSiteName + currentSiteNumber);
				Map<Locale, String> descriptionMap = new HashMap<>();
				descriptionMap.put(
				LocaleUtil.getDefault(), "");
				
				long userId = adminUserId;
				long parentGroupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;
				String className = Group.class.getName();
				long classPK = 0;
				long liveGroupId = GroupConstants.DEFAULT_LIVE_GROUP_ID;
				int type = GroupConstants.TYPE_SITE_OPEN;
				boolean manualMembership = true;
				int membershipRestriction = GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;
				String friendlyURL = "/" + newSiteName + currentSiteNumber;
				
				Group group = GroupLocalServiceUtil.addGroup(userId, parentGroupId, className, classPK, liveGroupId, nameMap, descriptionMap,
						type, manualMembership, membershipRestriction, friendlyURL, true, true, true, serviceContext);


				
				//Add the page
				Layout myPage =  LayoutLocalServiceUtil.addLayout(adminUserId, group.getGroupId(), false, 
				                                        0, "Page1", "Page1", "Page1", 
				                                        LayoutConstants.TYPE_PORTLET, false, "/page1", serviceContext);
				
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			} finally {
				try {
					System.out
							.println("Site count after site creation: "
									+ getSiteCount());
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
