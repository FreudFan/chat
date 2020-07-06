import Vue from 'vue'
import Vuex from 'vuex'
import VuexPersistence from 'vuex-persist'

Vue.use(Vuex)
const vuexLocal = new VuexPersistence({
    storage: window.sessionStorage
})
export default new Vuex.Store({
    state: {
        friendList: [
            {
                "groupId": 0,
                "groupName": "",
                "users": [
                    {
                        "id": 0,
                        "name": "",
                        "nickname": "",
                        "gender": 0,
                        "groupId": 0
                    }
                ]
            }
        ],
        groupList: [{
            "id": 0,
            "name": "",
            "masterId": 0,
            "info": "",
            "flag": 0,
            "createTime": ""
        }],
        currentTab: '好友',
        currentUser: null,
        queryResult: null,
        searchResult: null,
        recMsg: [],
    },
    mutations: {
        setCurrentTab(state, payload) {
            state.currentTab = payload;
        },
        setFriendList(state, payload) {
            state.friendList = payload;
        },
        setGroupList(state, payload) {
            state.groupList = payload;
        },
        setCurrentUser(state, payload) {
            state.currentUser = payload;
        },
        setQueryResult(state, payload) {
            state.queryResult = payload;
        },
        pushRecMsg(state, payload) {
            state.recMsg.push(payload);
        },
        setSearchResult(state, payload) {
            state.searchResult = payload;
        },
    },
    actions: {},
    modules: {},
    plugins: [vuexLocal.plugin]
})
