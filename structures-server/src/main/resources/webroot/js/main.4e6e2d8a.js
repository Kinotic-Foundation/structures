(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["main"],{"0326":function(e,t,a){"use strict";a.r(t);var n=a("0798"),s=a("7496f"),i=a("8336"),r=a("b0af"),c=a("99d9"),o=a("62ad"),l=a("a523"),u=a("0e8f"),d=a("4bd4"),p=a("adda"),f=a("a722"),b=a("f6c4"),g=a("0fd9"),h=a("2fa4"),v=a("8654"),O=function render(){var e=this,t=e._self._c;e._self._setupProxy;return t(s["a"],{staticClass:"login"},[t(b["a"],[t(l["a"],{attrs:{fluid:"","fill-height":""}},[t(f["a"],{attrs:{"align-center":"","justify-center":""}},[t(u["a"],{attrs:{xs12:"",sm8:"",md6:"",lg4:""}},[t(r["a"],{staticClass:"elevation-1 pa-3"},[t(c["b"],[t("div",{staticClass:"layout column align-center"},[t(p["a"],{attrs:{src:a("3f3a"),contain:""}})],1),t(d["a"],{ref:"form",attrs:{"lazy-validation":""},model:{value:e.valid,callback:function callback(t){e.valid=t},expression:"valid"}},[t(v["a"],{attrs:{"append-icon":e.icons.user,name:"login",label:"Login",type:"text",required:"",rules:e.loginRules,autofocus:""},on:{focus:e.hideAlert},model:{value:e.login,callback:function callback(t){e.login=t},expression:"login"}}),t(v["a"],{attrs:{"append-icon":e.icons.password,name:"password",label:"Password",id:"password",type:"password",required:"",rules:e.passwordRules},on:{"!keyup":function keyup(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleLogin.apply(null,arguments)},focus:e.hideAlert},model:{value:e.password,callback:function callback(t){e.password=t},expression:"password"}})],1)],1),t("div",[t(h["a"]),t(i["a"],{attrs:{block:"",color:"primary",loading:e.loading},on:{click:e.handleLogin}},[e._v(" Login ")])],1)],1)],1)],1)],1)],1),t("notifications",{attrs:{group:"alert",position:"bottom center",width:"100%",duration:3e4},scopedSlots:e._u([{key:"body",fn:function fn(a){return[t(n["a"],{attrs:{type:"error",prominent:""},on:{click:a.close}},[t(g["a"],{attrs:{align:"center"}},[t(o["a"],{staticClass:"grow"},[e._v(e._s(a.item.text))]),t(o["a"],{staticClass:"shrink"},[t(i["a"],{attrs:{color:"black",text:""},on:{click:a.close}},[e._v(" Close ")])],1)],1)],1)]}}])})],1)},y=[],j=a("c7eb"),m=a("1da1"),x=a("d4ec"),k=a("bee2"),S=a("8f33"),_=a("262e"),w=a("9ab4"),C=a("94ed"),A=a("1b40"),L=a("0a43"),U=a("cf9f"),F=function(e){function Login(){var e;return Object(x["a"])(this,Login),e=Object(S["a"])(this,Login,arguments),e.icons={user:C["a"],password:C["u"]},e.valid=!0,e.login="",e.password="",e.loading=!1,e.loginRules=[function(e){return!!e||"Login required"}],e.passwordRules=[function(e){return!!e||"Password required"}],e.userState=U["a"].getUserState(),e}return Object(_["a"])(Login,e),Object(k["a"])(Login,[{key:"handleLogin",value:function(){var e=Object(m["a"])(Object(j["a"])().mark((function _callee(){return Object(j["a"])().wrap((function _callee$(e){while(1)switch(e.prev=e.next){case 0:if(!this.$refs.form.validate()){e.next=18;break}return this.loading=!0,e.prev=2,e.next=5,this.userState.authenticate(this.login,this.password);case 5:if(!this.referer){e.next=10;break}return e.next=8,L["a"].navigate(this.referer);case 8:e.next=12;break;case 10:return e.next=12,L["a"].navigate("/");case 12:e.next=17;break;case 14:e.prev=14,e.t0=e["catch"](2),this.displayAlert(e.t0.message?e.t0.message:e.t0);case 17:this.loading=!1;case 18:case"end":return e.stop()}}),_callee,this,[[2,14]])})));function handleLogin(){return e.apply(this,arguments)}return handleLogin}()},{key:"hideAlert",value:function hideAlert(){this.$notify.close("loginAlert")}},{key:"displayAlert",value:function displayAlert(e){this.$notify({group:"alert",type:"loginAlert",text:e})}}]),Login}(A["e"]);Object(w["a"])([Object(A["c"])({type:String,required:!1,default:null}),Object(w["b"])("design:type",Object)],F.prototype,"referer",void 0),F=Object(w["a"])([Object(A["a"])({components:{}})],F);var I=F,P=I,q=(a("aa17"),a("2877")),$=Object(q["a"])(P,O,y,!1,null,"315fcaa4",null);t["default"]=$.exports},"2a12":function(e,t,a){"use strict";a("7cee")},"3f3a":function(e,t,a){e.exports=a.p+"img/logo.80481ddf.png"},4137:function(e,t,a){"use strict";a.r(t);var n=function render(){var e=this,t=e._self._c;e._self._setupProxy;return t("Glitch",{staticStyle:{"font-size":"10em"},attrs:{message:"404"}})},s=[],i=a("bee2"),r=a("d4ec"),c=a("8f33"),o=a("262e"),l=a("9ab4"),u=a("1b40"),d=a("1bc9"),p=function(e){function FourOFour(){return Object(r["a"])(this,FourOFour),Object(c["a"])(this,FourOFour,arguments)}return Object(o["a"])(FourOFour,e),Object(i["a"])(FourOFour)}(u["e"]);p=Object(l["a"])([Object(u["a"])({components:{Glitch:d["a"]}})],p);var f=p,b=f,g=a("2877"),h=Object(g["a"])(b,n,s,!1,null,null,null);t["default"]=h.exports},"5a18":function(e,t,a){"use strict";a.r(t);var n=function render(){var e=this,t=e._self._c;e._self._setupProxy;return t("Glitch",{staticStyle:{"font-size":"10em"},attrs:{message:"Credentials provided do not have access to resource"}})},s=[],i=a("bee2"),r=a("d4ec"),c=a("8f33"),o=a("262e"),l=a("9ab4"),u=a("1b40"),d=a("1bc9"),p=function(e){function AccessDenied(){return Object(r["a"])(this,AccessDenied),Object(c["a"])(this,AccessDenied,arguments)}return Object(o["a"])(AccessDenied,e),Object(i["a"])(AccessDenied)}(u["e"]);p=Object(l["a"])([Object(u["a"])({components:{Glitch:d["a"]}})],p);var f=p,b=f,g=a("2877"),h=Object(g["a"])(b,n,s,!1,null,null,null);t["default"]=h.exports},"7cee":function(e,t,a){},"90d1":function(e,t,a){"use strict";a.r(t);var n=a("7496f"),s=a("b0af"),i=a("99d9"),r=a("a523"),c=a("0e8f"),o=a("132d"),l=a("a722"),u=a("f6c4"),d=a("a797"),p=a("490a"),f=function render(){var e=this,t=e._self._c;e._self._setupProxy;return t(n["a"],{staticClass:"login"},[t(u["a"],[t(r["a"],{attrs:{fluid:"","fill-height":""}},[t(l["a"],{attrs:{"align-center":"","justify-center":""}},[t(c["a"],{attrs:{xs12:"",sm8:"",md6:"",lg4:""}},[t(s["a"],{staticClass:"elevation-1 pa-3"},[t(i["b"],[t("div",{staticClass:"layout column align-center"},[t(o["a"],{attrs:{"x-large":""}},[e._v(e._s(e.authenticated?e.lockOpen:e.lock))])],1),t("p",{staticClass:"text-h4 text-center",staticStyle:{"padding-top":"15px"}},[e._v(" "+e._s(e.title)+" ")]),t("p",{staticClass:"text-subtitle-2 text-center"},[e._v(" "+e._s(e.message)+" ")])]),t(d["a"],{attrs:{absolute:!0,value:e.loading}},[t(p["a"],{attrs:{indeterminate:"",size:"64"}})],1)],1)],1)],1)],1)],1)],1)},b=[],g=a("c7eb"),h=a("1da1"),v=a("d4ec"),O=a("bee2"),y=a("8f33"),j=a("262e"),m=a("9ab4"),x=a("1b40"),k=a("cf9f"),S=a("b9d8"),_=function(){function SessionUpgradeService(){Object(v["a"])(this,SessionUpgradeService),this.serviceProxy=S["b"].serviceProxy("continuum.cli.SessionUpgradeService")}return Object(O["a"])(SessionUpgradeService,[{key:"upgradeSession",value:function(){var e=Object(h["a"])(Object(g["a"])().mark((function _callee(e,t){return Object(g["a"])().wrap((function _callee$(a){while(1)switch(a.prev=a.next){case 0:return a.next=2,this.serviceProxy.invoke("upgradeSession",[t],e);case 2:case"end":return a.stop()}}),_callee,this)})));function upgradeSession(t,a){return e.apply(this,arguments)}return upgradeSession}()}]),SessionUpgradeService}(),w=new _,C=a("94ed"),A=function(e){function CliSessionUpgrade(){var e;return Object(v["a"])(this,CliSessionUpgrade),e=Object(y["a"])(this,CliSessionUpgrade,arguments),e.loading=!1,e.authenticated=!1,e.title="Authenticating",e.message="Connecting CLI to Continuum...",e.userState=k["a"].getUserState(),e.sessionUpgradeService=w,e.lockOpen=C["v"],e.lock=C["u"],e}return Object(j["a"])(CliSessionUpgrade,e),Object(O["a"])(CliSessionUpgrade,[{key:"mounted",value:function(){var e=Object(h["a"])(Object(g["a"])().mark((function _callee(){var e,t,a;return Object(g["a"])().wrap((function _callee$(n){while(1)switch(n.prev=n.next){case 0:if(!this.id){n.next=23;break}if(!(null===(t=null===(e=this.userState)||void 0===e?void 0:e.connectedInfo)||void 0===t?void 0:t.sessionId)){n.next=20;break}return this.loading=!0,n.prev=3,a=decodeURIComponent(this.id),n.next=7,this.sessionUpgradeService.upgradeSession(a,this.userState.connectedInfo.sessionId);case 7:n.next=12;break;case 9:n.prev=9,n.t0=n["catch"](3),this.setStatus("Error connecting CLI to Continuum: "+n.t0,!1,!1);case 12:return n.prev=12,this.setStatus("You can close this tab and return to your command line.",!0,!1),n.finish(12);case 15:return n.next=17,S["b"].disconnect(!0);case 17:this.loading=!1,n.next=21;break;case 20:this.setStatus("Error connecting CLI to Continuum: No session found.",!1,!1);case 21:n.next=24;break;case 23:this.setStatus("Error connecting CLI to Continuum: No upgrade id provided.",!1,!1);case 24:case"end":return n.stop()}}),_callee,this,[[3,9,12,15]])})));function mounted(){return e.apply(this,arguments)}return mounted}()},{key:"setStatus",value:function setStatus(e,t,a){this.title=a?"Authenticating":t?"Authentication Successful":"Authentication Failed",this.message=e,this.authenticated=t,this.loading=a}}]),CliSessionUpgrade}(x["e"]);Object(m["a"])([Object(x["c"])({type:String,required:!1,default:null}),Object(m["b"])("design:type",Object)],A.prototype,"id",void 0),A=Object(m["a"])([Object(x["a"])({components:{}})],A);var L=A,U=L,F=(a("2a12"),a("2877")),I=Object(F["a"])(U,f,b,!1,null,"4f3ab10c",null);t["default"]=I.exports},a2ec:function(e,t,a){},aa17:function(e,t,a){"use strict";a("a2ec")}}]);
//# sourceMappingURL=main.4e6e2d8a.js.map