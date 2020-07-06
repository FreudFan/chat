import Vue from 'vue'
import VueRouter from 'vue-router'
import Welcome from "../components/Welcome";
import Register from "../components/Register";
import Main from "../components/Main";
import EditInfo from "../components/EditInfo";
import SearchUser from "../components/SearchUser";
import SearchGroup from "../components/SearchGroup";
import FriendRequest from "../components/FriendRequest";
import FriendGroupSetting from "../components/FriendGroupSetting";
import FriendChat from "../components/FriendChat";
import FriendInfo from "../components/FriendInfo";
import GroupChat from "../components/GroupChat";
import GroupSetting from "../components/GroupSetting";
import CreateGroup from "../components/CreateGroup";
import GroupFile from "../components/GroupFile";
import GroupNotice from "../components/GroupNotice";
import FriendMsgLog from "../components/FriendMsgLog";

Vue.use(VueRouter)
const routes = [
    {
        path: '/',
        redirect: '/welcome'
    },
    {
        path: '/welcome',
        component: Welcome
    },
    {
        path: '/register',
        component: Register
    },
    {
        path: '/main',
        component: Main,
        children: [
            {
                path: 'editinfo',
                component: EditInfo
            },
            {
                path: 'searchuser',
                component: SearchUser
            },
            {
                path: 'searchgroup',
                component: SearchGroup
            },
            {
                path: 'friendrequest',
                component: FriendRequest
            },
            {
                path: 'friendgroupsetting',
                component: FriendGroupSetting
            },
            {
                path: 'friendchat',
                component: FriendChat
            },
            {
                path: 'friendinfo',
                component: FriendInfo
            },
            {
                path: 'groupchat',
                component: GroupChat
            },
            {
                path: 'groupsetting',
                component: GroupSetting
            },
            {
                path: 'creategroup',
                component: CreateGroup
            },
            {
                path: 'groupfile',
                component: GroupFile
            },
            {
                path: 'groupnotice',
                component: GroupNotice
            },
            {
                path: 'friendmsglog',
                component: FriendMsgLog
            },
        ]
    },
]

const router = new VueRouter({
    routes,
})

// 解决重复路由报错
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(to) {
    return VueRouterPush.call(this, to).catch(err => err)
}

export default router
