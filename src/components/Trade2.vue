<template>
  <div class="body" v-if="state">
    <section>
      <!-- <div class="top">
        <div class="top_left">
          <div>당신의 재능을</div>
          <div>사고 파세요</div>
        </div>
        <div class="top_right"></div>
      </div> -->

      <div class="main">
        <div class="main_left">
          <!-- 도와주세요 도와줄게요 탭 -->
          <div class="main_left-1">
            <div
              class="tab_buy"
              :class="{ tab: state.istab1 }"
              @click="handleTap('buy')"
            >
              <p>도와주세요</p>
            </div>
            <div
              class="tab_sell"
              :class="{ tab: state.istab2 }"
              @click="handleTap('sell')"
            >
              <p>도와줄게요</p>
            </div>
          </div>

          <!-- 글자랑 이미지 -->
          <div class="main_left-2" v-show="state.tab === 'buy'">
            <img :src="state.lense" class="img" />
            <span>무엇을 도와드릴까요?</span>
          </div>

          <div class="main_left-2" v-show="state.tab === 'sell'">
            <img :src="state.lense" class="img" />
            <span>당신의 도움이 필요해요</span>
          </div>

          <!-- 카테고리 셀렉 -->
          <div class="main_left-3">
            <div class="box2">
              <div class="box">
                <select
                  @change="handleOption($event)"
                  name=""
                  id=""
                  class="main_left-3-select"
                >
                  <option value="">카테고리</option>
                  <option
                    v-for="tmp of state.category"
                    :key="tmp"
                    :value="tmp.incategory"
                  >
                    {{ tmp.incategory }}
                  </option>
                </select>
                <img :src="state.droparrow" class="droparrow" />
              </div>
              <div class="box">
                <select
                  @change="handleoption2($event)"
                  ref="select"
                  name=""
                  id=""
                  class="main_left-3-select"
                >
                  <option value="">상세</option>
                  <option
                    v-for="tmp of state.list"
                    :key="tmp"
                    :value="tmp.inname"
                  >
                    {{ tmp.inname }}
                  </option>
                </select>
                <img :src="state.droparrow" class="droparrow" />
              </div>
            </div>
          </div>

          <!-- 검색 버튼 -->
          <div class="main_left-4" @click="clickSearch">검색</div>

          <!-- 검색 아래 카테고리 -->
          <div class="main_left-5">
            <span>{{ state.selectcategory }}</span>
            <span>{{ state.selectcategoryname }}</span>
            <img
              v-if="state.selectcategory !== ''"
              :src="state.close"
              @click="handleClose"
            />
          </div>
        </div>
        <div class="main_right">
          <v-img src="../assets/images/slide13.jpg" alt="" />
        </div>
      </div>
      <!-- 카테고리 -->
      <div style="margin-left: 20px"><h3>카테고리</h3></div>
      <div class="cate_box">
        <div class="cate_small" :class="{active:state.cateSmall[idx]}" @click="handleCateSmall(tmp,idx)" v-for="(tmp,idx) of state.category" :key="tmp">
          {{tmp.incategory}}
        </div>
      </div>
  <!-- {{state.cateSmall}} -->
    </section>

    

		<!-- 게시판 -->
    <div class="main2" v-if="state.boardComp">
      <div class="d-flex mb-6" style="margin-top: 30px">
        <div v-if="state.tab2==='buy'"><h3>도와주세요</h3></div>
        <div v-if="state.tab2==='sell'"><h3>도와줄게요</h3></div>
      </div>
      <div class="gridbox">
        <div class="helpme" v-for="tmp in state.board" :key="tmp">
          <ul class="helpmelist" style="margin-right: 10px; margin-left: 10px">
            <li>
              <a href="#">
                <div class="profile">
                  <div class="profile-item">
                    <v-img
                      src="../assets/images/user.png"
                      style="width: 25px; height: 25px"
                    />
                  </div>
									<p class="nickname">{{tmp.uid}}</p>
                </div>
              </a>
              
              <div class="imghover" style="cursor:pointer;" @click="imgClick(tmp.bno)">
                <div class="wrphover">
                  <div class="thumbnail">
										<v-img :src="`AbilityMarket/api/trade/image?bno=${tmp.bno}`"
									style="width:100%; height:100%; object-fit:cover;"/>
									</div>
                  <span class="new">NEW</span>
                </div>
                <div class="profilebottom">
                  <div class="check">
                    <v-img
                      :src= tmp.img
                      style="width: 30px; height: 30px"
                    />
                  </div>
                  <div class="profilebottom-item">
                    <p>{{tmp.btitle}}</p>
                  </div>
                </div>

                <div class="location">
                  <div class="location-item">
                    <v-img
                      src="../assets/images/location.png"
                      style="width: 20px; height: 20px; margin-bottom: 40px"
                    />
                  </div>
                  <div class="address"><p>{{tmp.baddress}}</p></div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>

			<!-- 페이지네이션 -->
			<div class="pagenation">
				<span v-for="tmp of state.page" :key="tmp">
					<!-- <div @click="clickpage(tmp)" style="display:inline-block; cursor:pointer; margin-left:5px" :href="`/AbilityMarket/api/trade/helpMe?page=${state.page}&brole=${state.brole}&incategory=${state.selectcategory}&inname=${state.selectcategoryname}`">{{tmp}}</div> -->
				</span>
			</div>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import axios from "axios";
// import { useStore } from "vuex";
import {useRouter} from "vue-router";

export default {
 
  setup() {
    const router = useRouter();
    const state = reactive({
      lense: require("../assets/images/lense.png"),
      droparrow: require("../assets/images/drop.png"),
      close: require("../assets/images/close.png"),
      list: "",
      tab: "buy",
      tab2: "buy",
      istab1: true,
      istab2: "",
      selectcategory: "",
      selectcategoryname: "",
      boardComp: false,
			page:1,
			brole: 1,
      cateSmall:[]
    });
    // const store = useStore();

    // 화면이 로딩되면 셀렉 옵션에 카테고리 목록이 만들어짐
    const handleData = async () => {
      const url = "/AbilityMarket/api/interest/select";
      const headers = { "content-type": "application/json" };
      const response = await axios.get(url, {headers});
      if (response.data.status === 200) {
        state.category = response.data.result;
        console.log("여기가 맞냐아? " + response);
        console.log(state.list);
      }
    };

    // 카테고리 목록을 고르면 상세 관심사들이 생성됨
    const handleOption = async (e) => {
      console.log(e.target.value);

      // 왼쪽 셀렉 카테고리 누르면 오른쪽 셀렉은 아무것도 선택 안되게
      if (e.target.value === "") {
        state.list = "";
        // state.selectcategory= "";
        return;
      }

      // 오른쪽 셀렉을 누르지 않았을 때
      if (state.selectcategoryname === "") {
        // state.selectcategory = e.target.value + " > 전체"
        state.selectcategoryname = "전체";
        state.selectcategory = e.target.value + " > ";
      }

      // 오른쪽 셀렉이 눌러져 있는데 왼쪽 셀렉을 바꿀 때
      else if (state.selectcategory !== "" && state.selectcategoryname !== "") {
        state.selectcategoryname = "전체";
        state.selectcategory = e.target.value + " > ";
      }

      // 오른쪽 셀렉이 선택될 때
      else if (state.selectcategory !== "") {
        state.selectcategory = e.target.value + " >" + state.selectcategoryname;
      }

      const url = `/AbilityMarket/api/interest/selectName?incategory=${e.target.value}`;
      const headers = { "content=type": "application/json" };
      const response = await axios.get(url, headers);
      if (response.data.status === 200) {
        state.list = response.data.result;
        console.log(state.list);
				
      }
    };

    // 오른쪽 셀렉 클릭 이벤트
    const handleoption2 = (e) => {
      if (e.target.value === "") {
        return;
      }
      console.log("here", e.target.value);
      state.selectcategoryname = e.target.value;
    };

    // x버튼 누를시
    const handleClose = () => {
      state.selectcategory = "";
      state.selectcategoryname = "";
    };

    // 검색버튼 누를시
    const clickSearch = async() => {
      state.boardComp = true;
			state.page=1;
      if(state.tab ==="sell"){
        state.tab2 = "sell";
      }
      else{
        state.tab2 = "buy";
      }
      
			const params = `page=${state.page}&brole=${state.brole}&incategory=${state.selectcategory.split(" ")[0]}&inname=${state.selectcategoryname}`
			const url = "/AbilityMarket/api/trade/helpMe?"+params
			const headers = {"content-type": "application/json"};
			const response = await axios.get(url, {headers})
			console.log(response);
			if(response.data.status===200){
				state.board = response.data.list
				state.page = response.data.page
        for(let i =0; i< response.data.list.length; i++){
          state.board[i].img = `/AbilityMarket/api/member/image?uid=${response.data.list[i].uid}`
          state.board[i].a_tag = `/AbilityMarket/api/board/selectone?bno=${response.data.list[i].bno}`
        }
			}
    };

    // 카테고리 클릭이벤트
    const handleCateSmall= async(v, idx)=>{
      console.log(v.incode, idx)
      state.cateSmall = Array(state.category.length).fill(false, 0);
      if(state.cateSmall[idx]===true){
        state.cateSmall[idx] = false;
      }
      else if(state.cateSmall[idx]===false){
        state.cateSmall[idx] = true;
      }

			const params = `page=${state.page}&brole=${state.brole}&incategory=${state.selectcategory.split(" ")[0]}&inname=${state.selectcategoryname}`
      const url = "/AbilityMarket/api/trade/helpMe?"+params
			const headers = {"content-type": "application/json"};
			const response = await axios.get(url, {headers})
			console.log(response);
			if(response.data.status===200){
				state.board = response.data.list
				state.page = response.data.page
			}
    }

    // 페이지네이션
		const clickpage = async(page)=>{
			const params = `page=${page}&brole=${state.brole}&incategory=${state.selectcategory.split(" ")[0]}&inname=${state.selectcategoryname}`
			const url = "/AbilityMarket/api/trade/helpMe?"+params
			const headers = {"content-type": "application/json"};
			const response = await axios.get(url, {headers})
			console.log(response);
			if(response.data.status===200){
				state.board = response.data.list
				state.page = response.data.page
			}
		}

    onMounted(() => {
      handleData();
    });

    const imgClick = (no)=>{
      router.push({name: "Detail", query:{bno: no}})
    }

    // 검색창 탭 이벤트
    const handleTap = (tab) => {
      // console.log(tab)
      if (tab === "buy") {
        console.log("buy");
        state.tab = "buy";
        state.istab1 = true;
        state.istab2 = false;
				state.brole = 1;
      } else if (tab === "sell") {
        console.log("sell");
        state.tab = "sell";
        state.istab1 = false;
        state.istab2 = true;
				state.brole = 2;
      }
    };

    return {
      state,
      handleOption,
      handleTap,
      handleoption2,
      handleClose,
      clickSearch,
			clickpage,
      handleCateSmall,
      imgClick
    };
  },
};
</script>

<style scoped src="../assets/css/trade2.css">
</style>
