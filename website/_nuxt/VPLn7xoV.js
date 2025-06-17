import{c as B}from"./Be1fzYNM.js";import{v as _,B as Z,Y as K,J as U,bn as c,M as F,c as l,o as n,q as u,Q as b,P as a,l as I,F as L,a as w,t as k,aa as V,S as X,L as C,w as j,b as Y,bo as q,s as H}from"./DIVREvd8.js";import{Z as v}from"./DGEOgZpe.js";import{s as J}from"./Dk-jlASV.js";import{s as z}from"./BOp_CX-A.js";import{s as O}from"./DwKHZOCK.js";import{s as T,a as E}from"./BeUuxcQs.js";import{s as Q}from"./DcKfR-gm.js";import{s as x}from"./C_T4oPjA.js";import{R as W}from"./DHxJ3ARZ.js";import"./fNKMJjk8.js";import"./zNb_omYy.js";import"./DmfvTtO6.js";var $=_`
    .p-toast {
        width: dt('toast.width');
        white-space: pre-line;
        word-break: break-word;
    }

    .p-toast-message {
        margin: 0 0 1rem 0;
    }

    .p-toast-message-icon {
        flex-shrink: 0;
        font-size: dt('toast.icon.size');
        width: dt('toast.icon.size');
        height: dt('toast.icon.size');
    }

    .p-toast-message-content {
        display: flex;
        align-items: flex-start;
        padding: dt('toast.content.padding');
        gap: dt('toast.content.gap');
    }

    .p-toast-message-text {
        flex: 1 1 auto;
        display: flex;
        flex-direction: column;
        gap: dt('toast.text.gap');
    }

    .p-toast-summary {
        font-weight: dt('toast.summary.font.weight');
        font-size: dt('toast.summary.font.size');
    }

    .p-toast-detail {
        font-weight: dt('toast.detail.font.weight');
        font-size: dt('toast.detail.font.size');
    }

    .p-toast-close-button {
        display: flex;
        align-items: center;
        justify-content: center;
        overflow: hidden;
        position: relative;
        cursor: pointer;
        background: transparent;
        transition:
            background dt('toast.transition.duration'),
            color dt('toast.transition.duration'),
            outline-color dt('toast.transition.duration'),
            box-shadow dt('toast.transition.duration');
        outline-color: transparent;
        color: inherit;
        width: dt('toast.close.button.width');
        height: dt('toast.close.button.height');
        border-radius: dt('toast.close.button.border.radius');
        margin: -25% 0 0 0;
        right: -25%;
        padding: 0;
        border: none;
        user-select: none;
    }

    .p-toast-close-button:dir(rtl) {
        margin: -25% 0 0 auto;
        left: -25%;
        right: auto;
    }

    .p-toast-message-info,
    .p-toast-message-success,
    .p-toast-message-warn,
    .p-toast-message-error,
    .p-toast-message-secondary,
    .p-toast-message-contrast {
        border-width: dt('toast.border.width');
        border-style: solid;
        backdrop-filter: blur(dt('toast.blur'));
        border-radius: dt('toast.border.radius');
    }

    .p-toast-close-icon {
        font-size: dt('toast.close.icon.size');
        width: dt('toast.close.icon.size');
        height: dt('toast.close.icon.size');
    }

    .p-toast-close-button:focus-visible {
        outline-width: dt('focus.ring.width');
        outline-style: dt('focus.ring.style');
        outline-offset: dt('focus.ring.offset');
    }

    .p-toast-message-info {
        background: dt('toast.info.background');
        border-color: dt('toast.info.border.color');
        color: dt('toast.info.color');
        box-shadow: dt('toast.info.shadow');
    }

    .p-toast-message-info .p-toast-detail {
        color: dt('toast.info.detail.color');
    }

    .p-toast-message-info .p-toast-close-button:focus-visible {
        outline-color: dt('toast.info.close.button.focus.ring.color');
        box-shadow: dt('toast.info.close.button.focus.ring.shadow');
    }

    .p-toast-message-info .p-toast-close-button:hover {
        background: dt('toast.info.close.button.hover.background');
    }

    .p-toast-message-success {
        background: dt('toast.success.background');
        border-color: dt('toast.success.border.color');
        color: dt('toast.success.color');
        box-shadow: dt('toast.success.shadow');
    }

    .p-toast-message-success .p-toast-detail {
        color: dt('toast.success.detail.color');
    }

    .p-toast-message-success .p-toast-close-button:focus-visible {
        outline-color: dt('toast.success.close.button.focus.ring.color');
        box-shadow: dt('toast.success.close.button.focus.ring.shadow');
    }

    .p-toast-message-success .p-toast-close-button:hover {
        background: dt('toast.success.close.button.hover.background');
    }

    .p-toast-message-warn {
        background: dt('toast.warn.background');
        border-color: dt('toast.warn.border.color');
        color: dt('toast.warn.color');
        box-shadow: dt('toast.warn.shadow');
    }

    .p-toast-message-warn .p-toast-detail {
        color: dt('toast.warn.detail.color');
    }

    .p-toast-message-warn .p-toast-close-button:focus-visible {
        outline-color: dt('toast.warn.close.button.focus.ring.color');
        box-shadow: dt('toast.warn.close.button.focus.ring.shadow');
    }

    .p-toast-message-warn .p-toast-close-button:hover {
        background: dt('toast.warn.close.button.hover.background');
    }

    .p-toast-message-error {
        background: dt('toast.error.background');
        border-color: dt('toast.error.border.color');
        color: dt('toast.error.color');
        box-shadow: dt('toast.error.shadow');
    }

    .p-toast-message-error .p-toast-detail {
        color: dt('toast.error.detail.color');
    }

    .p-toast-message-error .p-toast-close-button:focus-visible {
        outline-color: dt('toast.error.close.button.focus.ring.color');
        box-shadow: dt('toast.error.close.button.focus.ring.shadow');
    }

    .p-toast-message-error .p-toast-close-button:hover {
        background: dt('toast.error.close.button.hover.background');
    }

    .p-toast-message-secondary {
        background: dt('toast.secondary.background');
        border-color: dt('toast.secondary.border.color');
        color: dt('toast.secondary.color');
        box-shadow: dt('toast.secondary.shadow');
    }

    .p-toast-message-secondary .p-toast-detail {
        color: dt('toast.secondary.detail.color');
    }

    .p-toast-message-secondary .p-toast-close-button:focus-visible {
        outline-color: dt('toast.secondary.close.button.focus.ring.color');
        box-shadow: dt('toast.secondary.close.button.focus.ring.shadow');
    }

    .p-toast-message-secondary .p-toast-close-button:hover {
        background: dt('toast.secondary.close.button.hover.background');
    }

    .p-toast-message-contrast {
        background: dt('toast.contrast.background');
        border-color: dt('toast.contrast.border.color');
        color: dt('toast.contrast.color');
        box-shadow: dt('toast.contrast.shadow');
    }

    .p-toast-message-contrast .p-toast-detail {
        color: dt('toast.contrast.detail.color');
    }

    .p-toast-message-contrast .p-toast-close-button:focus-visible {
        outline-color: dt('toast.contrast.close.button.focus.ring.color');
        box-shadow: dt('toast.contrast.close.button.focus.ring.shadow');
    }

    .p-toast-message-contrast .p-toast-close-button:hover {
        background: dt('toast.contrast.close.button.hover.background');
    }

    .p-toast-top-center {
        transform: translateX(-50%);
    }

    .p-toast-bottom-center {
        transform: translateX(-50%);
    }

    .p-toast-center {
        min-width: 20vw;
        transform: translate(-50%, -50%);
    }

    .p-toast-message-enter-from {
        opacity: 0;
        transform: translateY(50%);
    }

    .p-toast-message-leave-from {
        max-height: 1000px;
    }

    .p-toast .p-toast-message.p-toast-message-leave-to {
        max-height: 0;
        opacity: 0;
        margin-bottom: 0;
        overflow: hidden;
    }

    .p-toast-message-enter-active {
        transition:
            transform 0.3s,
            opacity 0.3s;
    }

    .p-toast-message-leave-active {
        transition:
            max-height 0.45s cubic-bezier(0, 1, 0, 1),
            opacity 0.3s,
            margin-bottom 0.3s;
    }
`;function d(t){"@babel/helpers - typeof";return d=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},d(t)}function y(t,e,o){return(e=tt(e))in t?Object.defineProperty(t,e,{value:o,enumerable:!0,configurable:!0,writable:!0}):t[e]=o,t}function tt(t){var e=et(t,"string");return d(e)=="symbol"?e:e+""}function et(t,e){if(d(t)!="object"||!t)return t;var o=t[Symbol.toPrimitive];if(o!==void 0){var s=o.call(t,e);if(d(s)!="object")return s;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}var ot={root:function(e){var o=e.position;return{position:"fixed",top:o==="top-right"||o==="top-left"||o==="top-center"?"20px":o==="center"?"50%":null,right:(o==="top-right"||o==="bottom-right")&&"20px",bottom:(o==="bottom-left"||o==="bottom-right"||o==="bottom-center")&&"20px",left:o==="top-left"||o==="bottom-left"?"20px":o==="center"||o==="top-center"||o==="bottom-center"?"50%":null}}},st={root:function(e){var o=e.props;return["p-toast p-component p-toast-"+o.position]},message:function(e){var o=e.props;return["p-toast-message",{"p-toast-message-info":o.message.severity==="info"||o.message.severity===void 0,"p-toast-message-warn":o.message.severity==="warn","p-toast-message-error":o.message.severity==="error","p-toast-message-success":o.message.severity==="success","p-toast-message-secondary":o.message.severity==="secondary","p-toast-message-contrast":o.message.severity==="contrast"}]},messageContent:"p-toast-message-content",messageIcon:function(e){var o=e.props;return["p-toast-message-icon",y(y(y(y({},o.infoIcon,o.message.severity==="info"),o.warnIcon,o.message.severity==="warn"),o.errorIcon,o.message.severity==="error"),o.successIcon,o.message.severity==="success")]},messageText:"p-toast-message-text",summary:"p-toast-summary",detail:"p-toast-detail",closeButton:"p-toast-close-button",closeIcon:"p-toast-close-icon"},rt=Z.extend({name:"toast",style:$,classes:st,inlineStyles:ot}),nt={name:"BaseToast",extends:z,props:{group:{type:String,default:null},position:{type:String,default:"top-right"},autoZIndex:{type:Boolean,default:!0},baseZIndex:{type:Number,default:0},breakpoints:{type:Object,default:null},closeIcon:{type:String,default:void 0},infoIcon:{type:String,default:void 0},warnIcon:{type:String,default:void 0},errorIcon:{type:String,default:void 0},successIcon:{type:String,default:void 0},closeButtonProps:{type:null,default:null},onMouseEnter:{type:Function,default:void 0},onMouseLeave:{type:Function,default:void 0},onClick:{type:Function,default:void 0}},style:rt,provide:function(){return{$pcToast:this,$parentInstance:this}}};function m(t){"@babel/helpers - typeof";return m=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},m(t)}function at(t,e,o){return(e=it(e))in t?Object.defineProperty(t,e,{value:o,enumerable:!0,configurable:!0,writable:!0}):t[e]=o,t}function it(t){var e=ct(t,"string");return m(e)=="symbol"?e:e+""}function ct(t,e){if(m(t)!="object"||!t)return t;var o=t[Symbol.toPrimitive];if(o!==void 0){var s=o.call(t,e);if(m(s)!="object")return s;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}var D={name:"ToastMessage",hostName:"Toast",extends:z,emits:["close"],closeTimeout:null,createdAt:null,lifeRemaining:null,props:{message:{type:null,default:null},templates:{type:Object,default:null},closeIcon:{type:String,default:null},infoIcon:{type:String,default:null},warnIcon:{type:String,default:null},errorIcon:{type:String,default:null},successIcon:{type:String,default:null},closeButtonProps:{type:null,default:null}},mounted:function(){this.message.life&&(this.lifeRemaining=this.message.life,this.startTimeout())},beforeUnmount:function(){this.clearCloseTimeout()},methods:{startTimeout:function(){var e=this;this.createdAt=new Date().valueOf(),this.closeTimeout=setTimeout(function(){e.close({message:e.message,type:"life-end"})},this.lifeRemaining)},close:function(e){this.$emit("close",e)},onCloseClick:function(){this.clearCloseTimeout(),this.close({message:this.message,type:"close"})},clearCloseTimeout:function(){this.closeTimeout&&(clearTimeout(this.closeTimeout),this.closeTimeout=null)},onMessageClick:function(e){var o;!((o=this.props)===null||o===void 0)&&o.onClick&&this.props.onClick({originalEvent:e,message:this.message})},onMouseEnter:function(e){var o;if((o=this.props)!==null&&o!==void 0&&o.onMouseEnter){if(this.props.onMouseEnter({originalEvent:e,message:this.message}),e.defaultPrevented)return;this.message.life&&(this.lifeRemaining=this.createdAt+this.lifeRemaining-new Date().valueOf(),this.createdAt=null,this.clearCloseTimeout())}},onMouseLeave:function(e){var o;if((o=this.props)!==null&&o!==void 0&&o.onMouseLeave){if(this.props.onMouseLeave({originalEvent:e,message:this.message}),e.defaultPrevented)return;this.message.life&&this.startTimeout()}}},computed:{iconComponent:function(){return{info:!this.infoIcon&&E,success:!this.successIcon&&O,warn:!this.warnIcon&&T,error:!this.errorIcon&&x}[this.message.severity]},closeAriaLabel:function(){return this.$primevue.config.locale.aria?this.$primevue.config.locale.aria.close:void 0},dataP:function(){return B(at({},this.message.severity,this.message.severity))}},components:{TimesIcon:Q,InfoCircleIcon:E,CheckIcon:O,ExclamationTriangleIcon:T,TimesCircleIcon:x},directives:{ripple:W}};function f(t){"@babel/helpers - typeof";return f=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},f(t)}function A(t,e){var o=Object.keys(t);if(Object.getOwnPropertySymbols){var s=Object.getOwnPropertySymbols(t);e&&(s=s.filter(function(i){return Object.getOwnPropertyDescriptor(t,i).enumerable})),o.push.apply(o,s)}return o}function M(t){for(var e=1;e<arguments.length;e++){var o=arguments[e]!=null?arguments[e]:{};e%2?A(Object(o),!0).forEach(function(s){lt(t,s,o[s])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(o)):A(Object(o)).forEach(function(s){Object.defineProperty(t,s,Object.getOwnPropertyDescriptor(o,s))})}return t}function lt(t,e,o){return(e=ut(e))in t?Object.defineProperty(t,e,{value:o,enumerable:!0,configurable:!0,writable:!0}):t[e]=o,t}function ut(t){var e=dt(t,"string");return f(e)=="symbol"?e:e+""}function dt(t,e){if(f(t)!="object"||!t)return t;var o=t[Symbol.toPrimitive];if(o!==void 0){var s=o.call(t,e);if(f(s)!="object")return s;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}var mt=["data-p"],ft=["data-p"],pt=["data-p"],gt=["data-p"],bt=["aria-label","data-p"];function yt(t,e,o,s,i,r){var h=F("ripple");return n(),l("div",a({class:[t.cx("message"),o.message.styleClass],role:"alert","aria-live":"assertive","aria-atomic":"true","data-p":r.dataP},t.ptm("message"),{onClick:e[1]||(e[1]=function(){return r.onMessageClick&&r.onMessageClick.apply(r,arguments)}),onMouseenter:e[2]||(e[2]=function(){return r.onMouseEnter&&r.onMouseEnter.apply(r,arguments)}),onMouseleave:e[3]||(e[3]=function(){return r.onMouseLeave&&r.onMouseLeave.apply(r,arguments)})}),[o.templates.container?(n(),u(b(o.templates.container),{key:0,message:o.message,closeCallback:r.onCloseClick},null,8,["message","closeCallback"])):(n(),l("div",a({key:1,class:[t.cx("messageContent"),o.message.contentStyleClass]},t.ptm("messageContent")),[o.templates.message?(n(),u(b(o.templates.message),{key:1,message:o.message},null,8,["message"])):(n(),l(L,{key:0},[(n(),u(b(o.templates.messageicon?o.templates.messageicon:o.templates.icon?o.templates.icon:r.iconComponent&&r.iconComponent.name?r.iconComponent:"span"),a({class:t.cx("messageIcon")},t.ptm("messageIcon")),null,16,["class"])),w("div",a({class:t.cx("messageText"),"data-p":r.dataP},t.ptm("messageText")),[w("span",a({class:t.cx("summary"),"data-p":r.dataP},t.ptm("summary")),k(o.message.summary),17,pt),o.message.detail?(n(),l("div",a({key:0,class:t.cx("detail"),"data-p":r.dataP},t.ptm("detail")),k(o.message.detail),17,gt)):I("",!0)],16,ft)],64)),o.message.closable!==!1?(n(),l("div",V(a({key:2},t.ptm("buttonContainer"))),[X((n(),l("button",a({class:t.cx("closeButton"),type:"button","aria-label":r.closeAriaLabel,onClick:e[0]||(e[0]=function(){return r.onCloseClick&&r.onCloseClick.apply(r,arguments)}),autofocus:"","data-p":r.dataP},M(M({},o.closeButtonProps),t.ptm("closeButton"))),[(n(),u(b(o.templates.closeicon||"TimesIcon"),a({class:[t.cx("closeIcon"),o.closeIcon]},t.ptm("closeIcon")),null,16,["class"]))],16,bt)),[[h]])],16)):I("",!0)],16))],16,mt)}D.render=yt;function p(t){"@babel/helpers - typeof";return p=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},p(t)}function ht(t,e,o){return(e=vt(e))in t?Object.defineProperty(t,e,{value:o,enumerable:!0,configurable:!0,writable:!0}):t[e]=o,t}function vt(t){var e=wt(t,"string");return p(e)=="symbol"?e:e+""}function wt(t,e){if(p(t)!="object"||!t)return t;var o=t[Symbol.toPrimitive];if(o!==void 0){var s=o.call(t,e);if(p(s)!="object")return s;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}function St(t){return Ct(t)||kt(t)||It(t)||Pt()}function Pt(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function It(t,e){if(t){if(typeof t=="string")return S(t,e);var o={}.toString.call(t).slice(8,-1);return o==="Object"&&t.constructor&&(o=t.constructor.name),o==="Map"||o==="Set"?Array.from(t):o==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(o)?S(t,e):void 0}}function kt(t){if(typeof Symbol<"u"&&t[Symbol.iterator]!=null||t["@@iterator"]!=null)return Array.from(t)}function Ct(t){if(Array.isArray(t))return S(t)}function S(t,e){(e==null||e>t.length)&&(e=t.length);for(var o=0,s=Array(e);o<e;o++)s[o]=t[o];return s}var jt=0,Ot={name:"Toast",extends:nt,inheritAttrs:!1,emits:["close","life-end"],data:function(){return{messages:[]}},styleElement:null,mounted:function(){c.on("add",this.onAdd),c.on("remove",this.onRemove),c.on("remove-group",this.onRemoveGroup),c.on("remove-all-groups",this.onRemoveAllGroups),this.breakpoints&&this.createStyle()},beforeUnmount:function(){this.destroyStyle(),this.$refs.container&&this.autoZIndex&&v.clear(this.$refs.container),c.off("add",this.onAdd),c.off("remove",this.onRemove),c.off("remove-group",this.onRemoveGroup),c.off("remove-all-groups",this.onRemoveAllGroups)},methods:{add:function(e){e.id==null&&(e.id=jt++),this.messages=[].concat(St(this.messages),[e])},remove:function(e){var o=this.messages.findIndex(function(s){return s.id===e.message.id});o!==-1&&(this.messages.splice(o,1),this.$emit(e.type,{message:e.message}))},onAdd:function(e){this.group==e.group&&this.add(e)},onRemove:function(e){this.remove({message:e,type:"close"})},onRemoveGroup:function(e){this.group===e&&(this.messages=[])},onRemoveAllGroups:function(){var e=this;this.messages.forEach(function(o){return e.$emit("close",{message:o})}),this.messages=[]},onEnter:function(){this.autoZIndex&&v.set("modal",this.$refs.container,this.baseZIndex||this.$primevue.config.zIndex.modal)},onLeave:function(){var e=this;this.$refs.container&&this.autoZIndex&&U(this.messages)&&setTimeout(function(){v.clear(e.$refs.container)},200)},createStyle:function(){if(!this.styleElement&&!this.isUnstyled){var e;this.styleElement=document.createElement("style"),this.styleElement.type="text/css",K(this.styleElement,"nonce",(e=this.$primevue)===null||e===void 0||(e=e.config)===null||e===void 0||(e=e.csp)===null||e===void 0?void 0:e.nonce),document.head.appendChild(this.styleElement);var o="";for(var s in this.breakpoints){var i="";for(var r in this.breakpoints[s])i+=r+":"+this.breakpoints[s][r]+"!important;";o+=`
                        @media screen and (max-width: `.concat(s,`) {
                            .p-toast[`).concat(this.$attrSelector,`] {
                                `).concat(i,`
                            }
                        }
                    `)}this.styleElement.innerHTML=o}},destroyStyle:function(){this.styleElement&&(document.head.removeChild(this.styleElement),this.styleElement=null)}},computed:{dataP:function(){return B(ht({},this.position,this.position))}},components:{ToastMessage:D,Portal:J}};function g(t){"@babel/helpers - typeof";return g=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},g(t)}function R(t,e){var o=Object.keys(t);if(Object.getOwnPropertySymbols){var s=Object.getOwnPropertySymbols(t);e&&(s=s.filter(function(i){return Object.getOwnPropertyDescriptor(t,i).enumerable})),o.push.apply(o,s)}return o}function Tt(t){for(var e=1;e<arguments.length;e++){var o=arguments[e]!=null?arguments[e]:{};e%2?R(Object(o),!0).forEach(function(s){Et(t,s,o[s])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(o)):R(Object(o)).forEach(function(s){Object.defineProperty(t,s,Object.getOwnPropertyDescriptor(o,s))})}return t}function Et(t,e,o){return(e=xt(e))in t?Object.defineProperty(t,e,{value:o,enumerable:!0,configurable:!0,writable:!0}):t[e]=o,t}function xt(t){var e=At(t,"string");return g(e)=="symbol"?e:e+""}function At(t,e){if(g(t)!="object"||!t)return t;var o=t[Symbol.toPrimitive];if(o!==void 0){var s=o.call(t,e);if(g(s)!="object")return s;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}var Mt=["data-p"];function Rt(t,e,o,s,i,r){var h=C("ToastMessage"),N=C("Portal");return n(),u(N,null,{default:j(function(){return[w("div",a({ref:"container",class:t.cx("root"),style:t.sx("root",!0,{position:t.position}),"data-p":r.dataP},t.ptmi("root")),[Y(q,a({name:"p-toast-message",tag:"div",onEnter:r.onEnter,onLeave:r.onLeave},Tt({},t.ptm("transition"))),{default:j(function(){return[(n(!0),l(L,null,H(i.messages,function(P){return n(),u(h,{key:P.id,message:P,templates:t.$slots,closeIcon:t.closeIcon,infoIcon:t.infoIcon,warnIcon:t.warnIcon,errorIcon:t.errorIcon,successIcon:t.successIcon,closeButtonProps:t.closeButtonProps,unstyled:t.unstyled,onClose:e[0]||(e[0]=function(G){return r.remove(G)}),pt:t.pt},null,8,["message","templates","closeIcon","infoIcon","warnIcon","errorIcon","successIcon","closeButtonProps","unstyled","pt"])}),128))]}),_:1},16,["onEnter","onLeave"])],16,Mt)]}),_:1})}Ot.render=Rt;export{Ot as default};
