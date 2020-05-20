package com.cs.sys.service.realm;

import com.cs.sys.dao.SysUserDao;
import com.cs.sys.entity.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiroUserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher cMatcher = new HashedCredentialsMatcher();
        cMatcher.setHashAlgorithmName("MD5");
        cMatcher.setHashIterations(1);
        super.setCredentialsMatcher(cMatcher);
    }

    /*获取授权信息并返回*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /*获取用户认证信息并封装返回*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        SysUser user = sysUserDao.findUserByUserName(username);
        if(user==null)
            throw new UnknownAccountException();
        if(user.getValid()==0)
            throw new LockedAccountException();
        ByteSource credentialsSalt=
                ByteSource.Util.bytes(user.getSalt());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                credentialsSalt,
                this.getName());
        return info ;
    }
}
