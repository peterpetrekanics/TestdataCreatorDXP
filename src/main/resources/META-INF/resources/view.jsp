<%@ include file="/init.jsp" %>

<p>
	<b><liferay-ui:message key="TestdataCreatorDXP.caption"/></b>
</p>


<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.kernel.theme.PortletDisplay"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="TestdataCreatorDXP.model.UserHandlerModel"%>
<%@page import="com.liferay.portal.kernel.service.UserLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.service.UserGroupLocalServiceUtil"%>
<%@page import="javax.portlet.RenderRequest"%>
<%@page import="javax.portlet.ActionRequest"%>
<%@page import="javax.portlet.PortletURL"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

Current non-admin user count:
<%=UserLocalServiceUtil.getUsersCount()-2%>
<br>
Current userGroup count:
<%=UserGroupLocalServiceUtil.getUserGroupsCount()%>
<br>
<br>
____________________________________________
<br>
<b>User related tasks:</b>
<br>
<br>

<portlet:actionURL var="userCreatorURL" name="userCreatorURL" >
</portlet:actionURL>

<aui:form action="${userCreatorURL}">
	Enter a name for the new user(s):  <aui:input type="text" name="newUserName" value="newUserName"></aui:input>
	Enter how many users should be created <aui:input type="number" name="newUserCount" value="newUserCount"></aui:input>
	<aui:input type="submit" name="" value="CreateUsers" ></aui:input>
</aui:form>

-------------------------------
<br>

<portlet:resourceURL var="resourceUrl2">
	<portlet:param name="portletAction" value="deleteUsers" />
</portlet:resourceURL>

<a href="#" onclick="callServeResource2()">Click here to delete all non-admin users</a>
<script type="text/javascript">
	function callServeResource2(){
		AUI().use('aui-io-request', function(A){
			A.io.request('${resourceUrl2}', {
				on: {
	                success: function() {
	                 location.reload();
	                }
	           }
			}
			);
		});
	}
	</script>

<br>
<br>
____________________________________________
<br>
<b> Usergroup related tasks:</b>
<br>
<br>

<portlet:actionURL var="userGroupCreatorURL" name="userGroupCreatorURL" >
</portlet:actionURL>

<aui:form action="${userGroupCreatorURL}">
	Enter a name for the new userGroup(s): <aui:input type="text" name="newUserGroupName" value="newUserGroupName"></aui:input>
	Enter how many userGroups should be created: <aui:input type="number" name="newUserGroupCount" value="newUserGroupCount"></aui:input>
	<aui:input type="submit" name="" value="CreateUserGroups" ></aui:input>
</aui:form>

-------------------------------
<br>

<portlet:resourceURL var="resourceUrl4">
	<portlet:param name="portletAction" value="assignUsersToUserGroups" />
</portlet:resourceURL>

<form name="UserAssignerForm" id="userAssigner">
	Enter how many users should be assigned to each userGroup: <br> <input
		type="number" onkeypress='return validateQty(event);'
		name="assignedUserCount" min="1" min="1000000" value="5"> <br />
	<input type="button" value="Submit" onclick="callServeResource4()">
</form>

<script type="text/javascript">
function validateQty(event) {
    var key = window.event ? event.keyCode : event.which;
if (event.keyCode == 8 || event.keyCode == 46
 || event.keyCode == 37 || event.keyCode == 39) {
    return true;
} else if ( key < 48 ) {
    return false;
} else if ( key > 57) {
	return false;
}
else return true;
};
function callServeResource4(){
    AUI().use('aui-io-request', function(A){
        A.io.request('<%=resourceUrl4.toString()%>', {
				method : 'post',
				form : {
					id : 'userAssigner'
				},
				on : {
					success : function() {
						alert(this.get('responseData'));
						location.reload();
					}
				}
			});
		});
	}
</script>

<br>
-------------------------------
<br>

<portlet:resourceURL var="resourceUrl5">
	<portlet:param name="portletAction" value="deleteUserGroups" />
</portlet:resourceURL>

<a href="#" onclick="callServeResource5()">Click here to delete all userGroups</a>
<script type="text/javascript">
	function callServeResource5() {
		AUI().use('aui-io-request', function(A) {
			A.io.request('${resourceUrl5}', {
				on : {
					success : function() {
						location.reload();
					}
				}
			});
		});
	}
</script>
<br>
<br>
____________________________________________
<br>
<b>Site related tasks:</b>
<br>
<br>

<portlet:actionURL var="siteCreatorURL" name="siteCreatorURL" >
</portlet:actionURL>

<aui:form action="${siteCreatorURL}">
	Enter a name for the new site(s): <aui:input type="text" name="newSiteName" value="newSiteName"></aui:input>
	Enter how many sites should be created: <aui:input type="number" name="newSiteCount" value="newSiteCount"></aui:input>
	<aui:input type="submit" name="" value="CreateSites" ></aui:input>
</aui:form>
____________________________________________
<br>
<b> Creating pages on the current site:</b>
<br>
<br>

<portlet:actionURL var="pageCreatorURL" name="pageCreatorURL" >
</portlet:actionURL>

<aui:form action="${pageCreatorURL}">
	Enter a name for the new page(s): <aui:input type="text" name="newPageName" value="newPageName"></aui:input>
	Enter how many pages should be created: <aui:input type="number" name="newPageCount" value="newPageCount"></aui:input>
	<aui:input type="submit" name="" value="CreatePages" ></aui:input>
</aui:form>

____________________________________________
