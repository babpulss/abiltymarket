<template>
    <h2 class="inquire"> 문의게시판 </h2>
    <div class="main">
        
        <div class="banban">
                <div class="btn-box">
                    <router-link to="/InquireWrite"><v-btn class="btn">문의하기</v-btn></router-link>
                </div>
                <div class="search-box">
                    <input v-model="state.title" class="search" @keyup.enter="handleData()" placeholder="검색">
                    <v-btn class="btn1" @click="handleData()"> 검색 </v-btn>
                </div>
            </div>
        <!-- 문의내역이 있는경우 노출 -->
        <div v-if="state.empty5">
            <v-table class="table" >
                <thead>
                    <tr class="text-left">
                        <th class="text-left1"> 번호 </th>
                        <th class="text-left2"> 제목 </th>
                        <th class="text-left3"> 댓글유무 </th>
                        <th class="text-left4"> 작성일 </th>
                        
                    </tr>
                </thead>
                <tbody>
                    <a th:href="@{/item/selectone(code=${tmp.icode})}" th:text="${tmp.inqtitle}"></a>
                    <tr v-for="tmp in state.items" :key="tmp">
                        <td> {{ tmp.inqno }} </td>
                        <td class="link" @click="secretPage(tmp.inqno)"> 
                        <!-- <td class="link" @click="handleDetailPage(tmp.inqno)"> 수정 전, 상세페이지로 이동 -->
                        {{ tmp.inqtitle }} <v-img src="../../assets/images/자물쇠.png" style="width:20px; height:20px; float:right; "/></td>
                        <td v-if="tmp.inqtype == 0"> 완료 </td><td v-if="tmp.inqtype == 1"> 미완료 </td>   
                        <td> {{tmp.inqregdate.split("T")[0]}} {{tmp.inqregdate.split("T")[1].split(":")[0]}}
                             : {{tmp.inqregdate.split("T")[1].split(":")[1]}}
                        </td>
                    </tr>
                </tbody>
            </v-table>
            <div class="text-center">

                <v-pagination
                    v-model="state.page" :length="state.total" 
                    prev-icon="mdi-menu-left" next-icon="mdi-menu-right" @click="handleData()"
                ></v-pagination>
            </div>
        </div>

        <!-- 문의내역이 없는경우 노출 -->
        <div v-if="state.empty" class="content-box">
            <div class="in-content">
                <p class="in-content-c">문의 내역이 없습니다.</p>
                <p class="in-content-w">문의글을 작성해주세요.</p>
                 <router-link to="/InquireWrite"><v-btn class="btn2">문의하기</v-btn></router-link>
            </div>
        </div>
    </div>
    
</template>

<script>
import { reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
export default {
    setup(){
        const router = useRouter();
        const state = reactive({
            token : sessionStorage.getItem("TOKEN"),
            page : 1,
            title : '',
            select: 1,
            total: 0,
        })

        // 시크릿페이지로 이동 
        const secretPage = (inqno) => { 
            console.log(inqno);
            router.push({name:'Secret', query:{inqno:inqno}})
        }
        

        const handleData = async() => {
            const url = `/AbilityMarket/api/inquire/selectlist?page=${state.page}&title=${state.title}&select=${state.select}`;
            const headers = {
                "Content-Type" : "application/json",
                "token" : state.token
            };
            const response = await axios.get(url, {headers});
            console.log(response);
            if(response.data.status === 200) {
                
                state.items = response.data.list;
                state.title = response.data.title;
                state.total = response.data.total;
            
                state.empty5 = true; 
                state.empty = false;
                
            }
            else{
                if(response.data.status === 0) {
                    state.empty = true;
                    state.empty5 = false;
                }
            }
        }
        onMounted( () => {
            handleData();
        })

        return { 
            state, 
            handleData, 
            secretPage,            
        }
    }
}
</script>
<style scoped src= "../../assets/css/inquire-list.css">

</style>