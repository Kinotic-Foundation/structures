import{v as R,B as S,bi as a,A as l,D as B,H as P,aA as j,a6 as H,W as F,L as C,M as K,q as g,o as c,w as p,b as y,T as I,P as r,S as D,l as w,c as u,N as f,F as T,a as d,Q as z,O as N,t as U,R as A}from"./DZN9uhBn.js";import{Z as L}from"./DGEOgZpe.js";import{C as V}from"./CcH2w7Jn.js";import q from"./BvzDP3wj.js";import{F as Z}from"./-4DJieQp.js";import{O as M}from"./CZighkSe.js";import{s as Q}from"./BAYyFvuT.js";import{s as W}from"./1noTU6vz.js";import"./Be1fzYNM.js";import"./CF46Zw-l.js";import"./C1vqh6FR.js";import"./B7VPv7zj.js";import"./CP7dxGoz.js";import"./DX3-MJaN.js";import"./DmfvTtO6.js";var Y=R`
    .p-confirmpopup {
        position: absolute;
        margin-top: dt('confirmpopup.gutter');
        top: 0;
        left: 0;
        background: dt('confirmpopup.background');
        color: dt('confirmpopup.color');
        border: 1px solid dt('confirmpopup.border.color');
        border-radius: dt('confirmpopup.border.radius');
        box-shadow: dt('confirmpopup.shadow');
    }

    .p-confirmpopup-content {
        display: flex;
        align-items: center;
        padding: dt('confirmpopup.content.padding');
        gap: dt('confirmpopup.content.gap');
    }

    .p-confirmpopup-icon {
        font-size: dt('confirmpopup.icon.size');
        width: dt('confirmpopup.icon.size');
        height: dt('confirmpopup.icon.size');
        color: dt('confirmpopup.icon.color');
    }

    .p-confirmpopup-footer {
        display: flex;
        justify-content: flex-end;
        gap: dt('confirmpopup.footer.gap');
        padding: dt('confirmpopup.footer.padding');
    }

    .p-confirmpopup-footer button {
        width: auto;
    }

    .p-confirmpopup-footer button:last-child {
        margin: 0;
    }

    .p-confirmpopup-flipped {
        margin-block-start: calc(dt('confirmpopup.gutter') * -1);
        margin-block-end: dt('confirmpopup.gutter');
    }

    .p-confirmpopup-enter-from {
        opacity: 0;
        transform: scaleY(0.8);
    }

    .p-confirmpopup-leave-to {
        opacity: 0;
    }

    .p-confirmpopup-enter-active {
        transition:
            transform 0.12s cubic-bezier(0, 0, 0.2, 1),
            opacity 0.12s cubic-bezier(0, 0, 0.2, 1);
    }

    .p-confirmpopup-leave-active {
        transition: opacity 0.1s linear;
    }

    .p-confirmpopup:after,
    .p-confirmpopup:before {
        bottom: 100%;
        left: calc(dt('confirmpopup.arrow.offset') + dt('confirmpopup.arrow.left'));
        content: ' ';
        height: 0;
        width: 0;
        position: absolute;
        pointer-events: none;
    }

    .p-confirmpopup:after {
        border-width: calc(dt('confirmpopup.gutter') - 2px);
        margin-left: calc(-1 * (dt('confirmpopup.gutter') - 2px));
        border-style: solid;
        border-color: transparent;
        border-bottom-color: dt('confirmpopup.background');
    }

    .p-confirmpopup:before {
        border-width: dt('confirmpopup.gutter');
        margin-left: calc(-1 * dt('confirmpopup.gutter'));
        border-style: solid;
        border-color: transparent;
        border-bottom-color: dt('confirmpopup.border.color');
    }

    .p-confirmpopup-flipped:after,
    .p-confirmpopup-flipped:before {
        bottom: auto;
        top: 100%;
    }

    .p-confirmpopup-flipped:after {
        border-bottom-color: transparent;
        border-top-color: dt('confirmpopup.background');
    }

    .p-confirmpopup-flipped:before {
        border-bottom-color: transparent;
        border-top-color: dt('confirmpopup.border.color');
    }
`,G={root:"p-confirmpopup p-component",content:"p-confirmpopup-content",icon:"p-confirmpopup-icon",message:"p-confirmpopup-message",footer:"p-confirmpopup-footer",pcRejectButton:"p-confirmpopup-reject-button",pcAcceptButton:"p-confirmpopup-accept-button"},J=S.extend({name:"confirmpopup",style:Y,classes:G}),X={name:"BaseConfirmPopup",extends:W,props:{group:String},style:J,provide:function(){return{$pcConfirmPopup:this,$parentInstance:this}}},$={name:"ConfirmPopup",extends:X,inheritAttrs:!1,data:function(){return{visible:!1,confirmation:null,autoFocusAccept:null,autoFocusReject:null,target:null}},target:null,outsideClickListener:null,scrollHandler:null,resizeListener:null,container:null,confirmListener:null,closeListener:null,mounted:function(){var e=this;this.confirmListener=function(n){n&&n.group===e.group&&(e.confirmation=n,e.target=n.target,e.confirmation.onShow&&e.confirmation.onShow(),e.visible=!0)},this.closeListener=function(){e.visible=!1,e.confirmation=null},a.on("confirm",this.confirmListener),a.on("close",this.closeListener)},beforeUnmount:function(){a.off("confirm",this.confirmListener),a.off("close",this.closeListener),this.unbindOutsideClickListener(),this.scrollHandler&&(this.scrollHandler.destroy(),this.scrollHandler=null),this.unbindResizeListener(),this.container&&(L.clear(this.container),this.container=null),this.target=null,this.confirmation=null},methods:{accept:function(){this.confirmation.accept&&this.confirmation.accept(),this.visible=!1},reject:function(){this.confirmation.reject&&this.confirmation.reject(),this.visible=!1},onHide:function(){this.confirmation.onHide&&this.confirmation.onHide(),this.visible=!1},onAcceptKeydown:function(e){(e.code==="Space"||e.code==="Enter"||e.code==="NumpadEnter")&&(this.accept(),l(this.target),e.preventDefault())},onRejectKeydown:function(e){(e.code==="Space"||e.code==="Enter"||e.code==="NumpadEnter")&&(this.reject(),l(this.target),e.preventDefault())},onEnter:function(e){this.autoFocusAccept=this.confirmation.defaultFocus===void 0||this.confirmation.defaultFocus==="accept",this.autoFocusReject=this.confirmation.defaultFocus==="reject",this.target=this.target||document.activeElement,this.bindOutsideClickListener(),this.bindScrollListener(),this.bindResizeListener(),L.set("overlay",e,this.$primevue.config.zIndex.overlay)},onAfterEnter:function(){this.focus()},onLeave:function(){this.autoFocusAccept=null,this.autoFocusReject=null,l(this.target),this.target=null,this.unbindOutsideClickListener(),this.unbindScrollListener(),this.unbindResizeListener()},onAfterLeave:function(e){L.clear(e)},alignOverlay:function(){P(this.container,this.target,!1);var e=j(this.container),n=j(this.target),m=0;e.left<n.left&&(m=n.left-e.left),this.container.style.setProperty(H("confirmpopup.arrow.left").name,"".concat(m,"px")),e.top<n.top&&(this.container.setAttribute("data-p-confirmpopup-flipped","true"),!this.isUnstyled&&F(this.container,"p-confirmpopup-flipped"))},bindOutsideClickListener:function(){var e=this;this.outsideClickListener||(this.outsideClickListener=function(n){e.visible&&e.container&&!e.container.contains(n.target)&&!e.isTargetClicked(n)?(e.confirmation.onHide&&e.confirmation.onHide(),e.visible=!1):e.alignOverlay()},document.addEventListener("click",this.outsideClickListener))},unbindOutsideClickListener:function(){this.outsideClickListener&&(document.removeEventListener("click",this.outsideClickListener),this.outsideClickListener=null)},bindScrollListener:function(){var e=this;this.scrollHandler||(this.scrollHandler=new V(this.target,function(){e.visible&&(e.visible=!1)})),this.scrollHandler.bindScrollListener()},unbindScrollListener:function(){this.scrollHandler&&this.scrollHandler.unbindScrollListener()},bindResizeListener:function(){var e=this;this.resizeListener||(this.resizeListener=function(){e.visible&&!B()&&(e.visible=!1)},window.addEventListener("resize",this.resizeListener))},unbindResizeListener:function(){this.resizeListener&&(window.removeEventListener("resize",this.resizeListener),this.resizeListener=null)},focus:function(){var e=this.container.querySelector("[autofocus]");e&&e.focus({preventScroll:!0})},isTargetClicked:function(e){return this.target&&(this.target===e.target||this.target.contains(e.target))},containerRef:function(e){this.container=e},onOverlayClick:function(e){M.emit("overlay-click",{originalEvent:e,target:this.target})},onOverlayKeydown:function(e){e.code==="Escape"&&(a.emit("close",this.closeListener),l(this.target))}},computed:{message:function(){return this.confirmation?this.confirmation.message:null},acceptLabel:function(){if(this.confirmation){var e,n=this.confirmation;return n.acceptLabel||((e=n.acceptProps)===null||e===void 0?void 0:e.label)||this.$primevue.config.locale.accept}return this.$primevue.config.locale.accept},rejectLabel:function(){if(this.confirmation){var e,n=this.confirmation;return n.rejectLabel||((e=n.rejectProps)===null||e===void 0?void 0:e.label)||this.$primevue.config.locale.reject}return this.$primevue.config.locale.reject},acceptIcon:function(){var e;return this.confirmation?this.confirmation.acceptIcon:(e=this.confirmation)!==null&&e!==void 0&&e.acceptProps?this.confirmation.acceptProps.icon:null},rejectIcon:function(){var e;return this.confirmation?this.confirmation.rejectIcon:(e=this.confirmation)!==null&&e!==void 0&&e.rejectProps?this.confirmation.rejectProps.icon:null}},components:{Button:q,Portal:Q},directives:{focustrap:Z}},x=["aria-modal"];function _(t,e,n,m,i,o){var k=C("Button"),O=C("Portal"),E=K("focustrap");return c(),g(O,null,{default:p(function(){return[y(I,r({name:"p-confirmpopup",onEnter:o.onEnter,onAfterEnter:o.onAfterEnter,onLeave:o.onLeave,onAfterLeave:o.onAfterLeave},t.ptm("transition")),{default:p(function(){var h,v,b;return[i.visible?D((c(),u("div",r({key:0,ref:o.containerRef,role:"alertdialog",class:t.cx("root"),"aria-modal":i.visible,onClick:e[2]||(e[2]=function(){return o.onOverlayClick&&o.onOverlayClick.apply(o,arguments)}),onKeydown:e[3]||(e[3]=function(){return o.onOverlayKeydown&&o.onOverlayKeydown.apply(o,arguments)})},t.ptmi("root")),[t.$slots.container?f(t.$slots,"container",{key:0,message:i.confirmation,acceptCallback:o.accept,rejectCallback:o.reject}):(c(),u(T,{key:1},[t.$slots.message?(c(),g(z(t.$slots.message),{key:1,message:i.confirmation},null,8,["message"])):(c(),u("div",r({key:0,class:t.cx("content")},t.ptm("content")),[f(t.$slots,"icon",{},function(){return[t.$slots.icon?(c(),g(z(t.$slots.icon),{key:0,class:N(t.cx("icon"))},null,8,["class"])):i.confirmation.icon?(c(),u("span",r({key:1,class:[i.confirmation.icon,t.cx("icon")]},t.ptm("icon")),null,16)):w("",!0)]}),d("span",r({class:t.cx("message")},t.ptm("message")),U(i.confirmation.message),17)],16)),d("div",r({class:t.cx("footer")},t.ptm("footer")),[y(k,r({class:[t.cx("pcRejectButton"),i.confirmation.rejectClass],autofocus:i.autoFocusReject,unstyled:t.unstyled,size:((h=i.confirmation.rejectProps)===null||h===void 0?void 0:h.size)||"small",text:((v=i.confirmation.rejectProps)===null||v===void 0?void 0:v.text)||!1,onClick:e[0]||(e[0]=function(s){return o.reject()}),onKeydown:o.onRejectKeydown},i.confirmation.rejectProps,{label:o.rejectLabel,pt:t.ptm("pcRejectButton")}),A({_:2},[o.rejectIcon||t.$slots.rejecticon?{name:"icon",fn:p(function(s){return[f(t.$slots,"rejecticon",{},function(){return[d("span",r({class:[o.rejectIcon,s.class]},t.ptm("pcRejectButton").icon,{"data-pc-section":"rejectbuttonicon"}),null,16)]})]}),key:"0"}:void 0]),1040,["class","autofocus","unstyled","size","text","onKeydown","label","pt"]),y(k,r({class:[t.cx("pcAcceptButton"),i.confirmation.acceptClass],autofocus:i.autoFocusAccept,unstyled:t.unstyled,size:((b=i.confirmation.acceptProps)===null||b===void 0?void 0:b.size)||"small",onClick:e[1]||(e[1]=function(s){return o.accept()}),onKeydown:o.onAcceptKeydown},i.confirmation.acceptProps,{label:o.acceptLabel,pt:t.ptm("pcAcceptButton")}),A({_:2},[o.acceptIcon||t.$slots.accepticon?{name:"icon",fn:p(function(s){return[f(t.$slots,"accepticon",{},function(){return[d("span",r({class:[o.acceptIcon,s.class]},t.ptm("pcAcceptButton").icon,{"data-pc-section":"acceptbuttonicon"}),null,16)]})]}),key:"0"}:void 0]),1040,["class","autofocus","unstyled","size","onKeydown","label","pt"])],16)],64))],16,x)),[[E]]):w("",!0)]}),_:3},16,["onEnter","onAfterEnter","onLeave","onAfterLeave"])]}),_:3})}$.render=_;export{$ as default};
