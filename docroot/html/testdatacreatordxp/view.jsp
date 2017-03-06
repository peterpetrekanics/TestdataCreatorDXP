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
<%@page import="com.liferay.model.UserHandlerModel"%>
<%@page import="com.liferay.portal.kernel.service.UserLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.service.UserGroupLocalServiceUtil"%>
<%@page import="javax.portlet.RenderRequest"%>
<%@page import="javax.portlet.ActionRequest"%>
<%@page import="javax.portlet.PortletURL"%>

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
<portlet:resourceURL var="resourceUrl1">
	<portlet:param name="portletAction" value="createUsers" />
</portlet:resourceURL>

<form name="newUserCreatorForm" id="newusers">
	Enter a name for the new user(s): <input type="text" name="newUserName" />
	<br> Enter how many users should be created <input type="number"
		name="newUserCount" onkeypress='return validateQty(event);' min="1"
		value="5"> <br /> <input type="button" value="Create Users"
		onclick="callServeResource1()">
</form>

<script type="text/javascript">
function callServeResource1(){
    AUI().use('aui-io-request', function(A){
        A.io.request('<%=resourceUrl1.toString()%>', {
               method: 'post',
               form: {
                   id: 'newusers'
               },
               on: {
                    success: function() {
                     alert(this.get('responseData'));
                     location.reload();
                    }
               }
            });
    });
}
</script>

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
<portlet:resourceURL var="resourceUrl3">
	<portlet:param name="portletAction" value="createUserGroups" />
</portlet:resourceURL>

<form name="newUserGroupCreatorForm" id="newUserGroups">
	Enter a name for the new userGroup(s): <input type="text"
		name="newUserGroupName" /> <br> Enter how many userGroups should
	be created <input type="number" name="newUserGroupCount"
		onkeypress='return validateQty(event);' min="1" value="5"> <br />
	<input type="button" value="Create UserGroups" onclick="callServeResource3()">
</form>

<script type="text/javascript">
function callServeResource3(){
    AUI().use('aui-io-request', function(A){
        A.io.request('<%=resourceUrl3.toString()%>', {
               method: 'post',
               form: {
                   id: 'newUserGroups'
               },
               on: {
                    success: function() {
                     alert(this.get('responseData'));
                     location.reload();
                    }
               }
            });
    });
}
</script>

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
<portlet:resourceURL var="resourceUrl6">
	<portlet:param name="portletAction" value="createSites" />
</portlet:resourceURL>

<form name="newSiteCreatorForm" id="newsites">
	Enter a name for the new site(s): <input type="text" name="newSiteName" />
	<br> Enter how many sites should be created <input type="number"
		name="newSiteCount" onkeypress='return validateQty(event);' min="1"
		value="5"> <br /> <input type="button" value="Create Sites"
		onclick="callServeResource6()">
</form>

<script type="text/javascript">
function callServeResource6(){
    AUI().use('aui-io-request', function(A){
        A.io.request('<%=resourceUrl6.toString()%>', {
               method: 'post',
               form: {
                   id: 'newsites'
               },
               on: {
                    success: function() {
                     alert(this.get('responseData'));
                     location.reload();
                    }
               }
            });
    });
}
</script>

-------------------------------
Contact GitHub API Training Shop Blog About
� 2017 GitHub, Inc. Terms Privacy Security Status Help