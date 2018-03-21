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
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;
import java.util.Locale;

public class SiteHandlerModel {

	public void createPage(long companyId, long adminUserId, long currentSiteIdOfUser, String newPageName, int newPageCount) {
		ServiceContext serviceContext = new ServiceContext();		
		for (int currentPageNumber = 1; currentPageNumber <= newPageCount; currentPageNumber++) {
			try {
				//Add the page
				Layout myPage =  LayoutLocalServiceUtil.addLayout(adminUserId, currentSiteIdOfUser, false, 
				                                        0, "Page" + currentPageNumber, "Page" + currentPageNumber, "Page" + currentPageNumber, 
				                                        LayoutConstants.TYPE_PORTLET, false, "/page"+currentPageNumber, serviceContext);
				
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			} finally {
//				try {
//					System.out
//							.println("Site count after site creation: "
//									+ getSiteCount());
//				} catch (SystemException e) {
//					e.printStackTrace();
//				}
			}
			}
		
	}
	
	

}
