import{v as d,B as f,aY as m,L as h,q as o,o as r,w as c,l as v,P as i,N as b,O as L,Q as y,T as S}from"./CwV9-nPG.js";import{Z as s}from"./DGEOgZpe.js";import{s as w}from"./DQJb6SuV.js";import g from"./DNPMPQs0.js";import{s as k}from"./BZ7rLBd_.js";import"./BrX9DCZU.js";import"./Be1fzYNM.js";import"./D0Unpx8V.js";import"./SfsMxs55.js";import"./DD1oJGt6.js";import"./BaRcc8XN.js";import"./DmfvTtO6.js";var E=d`
    .p-scrolltop.p-button {
        position: fixed !important;
        inset-block-end: 20px;
        inset-inline-end: 20px;
    }

    .p-scrolltop-sticky.p-button {
        position: sticky !important;
        display: flex;
        margin-inline-start: auto;
    }

    .p-scrolltop-enter-from {
        opacity: 0;
    }

    .p-scrolltop-enter-active {
        transition: opacity 0.15s;
    }

    .p-scrolltop.p-scrolltop-leave-to {
        opacity: 0;
    }

    .p-scrolltop-leave-active {
        transition: opacity 0.15s;
    }
`,$={root:function(t){var l=t.props;return["p-scrolltop",{"p-scrolltop-sticky":l.target!=="window"}]},icon:"p-scrolltop-icon"},C=f.extend({name:"scrolltop",style:E,classes:$}),T={name:"BaseScrollTop",extends:k,props:{target:{type:String,default:"window"},threshold:{type:Number,default:400},icon:{type:String,default:void 0},behavior:{type:String,default:"smooth"},buttonProps:{type:Object,default:function(){return{rounded:!0}}}},style:C,provide:function(){return{$pcScrollTop:this,$parentInstance:this}}},P={name:"ScrollTop",extends:T,inheritAttrs:!1,scrollListener:null,container:null,data:function(){return{visible:!1}},mounted:function(){this.target==="window"?this.bindDocumentScrollListener():this.target==="parent"&&this.bindParentScrollListener()},beforeUnmount:function(){this.target==="window"?this.unbindDocumentScrollListener():this.target==="parent"&&this.unbindParentScrollListener(),this.container&&(s.clear(this.container),this.overlay=null)},methods:{onClick:function(){var t=this.target==="window"?window:this.$el.parentElement;t.scroll({top:0,behavior:this.behavior})},checkVisibility:function(t){t>this.threshold?this.visible=!0:this.visible=!1},bindParentScrollListener:function(){var t=this;this.scrollListener=function(){t.checkVisibility(t.$el.parentElement.scrollTop)},this.$el.parentElement.addEventListener("scroll",this.scrollListener)},bindDocumentScrollListener:function(){var t=this;this.scrollListener=function(){t.checkVisibility(m())},window.addEventListener("scroll",this.scrollListener)},unbindParentScrollListener:function(){this.scrollListener&&(this.$el.parentElement.removeEventListener("scroll",this.scrollListener),this.scrollListener=null)},unbindDocumentScrollListener:function(){this.scrollListener&&(window.removeEventListener("scroll",this.scrollListener),this.scrollListener=null)},onEnter:function(t){s.set("overlay",t,this.$primevue.config.zIndex.overlay)},onAfterLeave:function(t){s.clear(t)},containerRef:function(t){this.container=t?t.$el:void 0}},computed:{scrollTopAriaLabel:function(){return this.$primevue.config.locale.aria?this.$primevue.config.locale.aria.scrollTop:void 0}},components:{ChevronUpIcon:w,Button:g}};function A(e,t,l,B,a,n){var p=h("Button");return r(),o(S,i({name:"p-scrolltop",appear:"",onEnter:n.onEnter,onAfterLeave:n.onAfterLeave},e.ptm("transition")),{default:c(function(){return[a.visible?(r(),o(p,i({key:0,ref:n.containerRef,class:e.cx("root"),onClick:n.onClick,"aria-label":n.scrollTopAriaLabel,unstyled:e.unstyled},e.buttonProps,{pt:e.ptm("root")}),{icon:c(function(u){return[b(e.$slots,"icon",{class:L(e.cx("icon"))},function(){return[(r(),o(y(e.icon?"span":"ChevronUpIcon"),i({class:[e.cx("icon"),e.icon,u.class]},e.ptm("root").icon,{"data-pc-section":"icon"}),null,16,["class"]))]})]}),_:3},16,["class","onClick","aria-label","unstyled","pt"])):v("",!0)]}),_:3},16,["onEnter","onAfterLeave"])}P.render=A;export{P as default};
