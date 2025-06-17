import{c as d}from"./Be1fzYNM.js";import{s as v}from"./BOp_CX-A.js";import{v as u,B as c,c as n,o as i,N as g,q as f,l as m,P as l,t as y,O as b,Q as h}from"./DIVREvd8.js";var z=u`
    .p-avatar {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        width: dt('avatar.width');
        height: dt('avatar.height');
        font-size: dt('avatar.font.size');
        background: dt('avatar.background');
        color: dt('avatar.color');
        border-radius: dt('avatar.border.radius');
    }

    .p-avatar-image {
        background: transparent;
    }

    .p-avatar-circle {
        border-radius: 50%;
    }

    .p-avatar-circle img {
        border-radius: 50%;
    }

    .p-avatar-icon {
        font-size: dt('avatar.icon.size');
        width: dt('avatar.icon.size');
        height: dt('avatar.icon.size');
    }

    .p-avatar img {
        width: 100%;
        height: 100%;
    }

    .p-avatar-lg {
        width: dt('avatar.lg.width');
        height: dt('avatar.lg.width');
        font-size: dt('avatar.lg.font.size');
    }

    .p-avatar-lg .p-avatar-icon {
        font-size: dt('avatar.lg.icon.size');
        width: dt('avatar.lg.icon.size');
        height: dt('avatar.lg.icon.size');
    }

    .p-avatar-xl {
        width: dt('avatar.xl.width');
        height: dt('avatar.xl.width');
        font-size: dt('avatar.xl.font.size');
    }

    .p-avatar-xl .p-avatar-icon {
        font-size: dt('avatar.xl.icon.size');
        width: dt('avatar.xl.icon.size');
        height: dt('avatar.xl.icon.size');
    }

    .p-avatar-group {
        display: flex;
        align-items: center;
    }

    .p-avatar-group .p-avatar + .p-avatar {
        margin-inline-start: dt('avatar.group.offset');
    }

    .p-avatar-group .p-avatar {
        border: 2px solid dt('avatar.group.border.color');
    }

    .p-avatar-group .p-avatar-lg + .p-avatar-lg {
        margin-inline-start: dt('avatar.lg.group.offset');
    }

    .p-avatar-group .p-avatar-xl + .p-avatar-xl {
        margin-inline-start: dt('avatar.xl.group.offset');
    }
`,S={root:function(t){var r=t.props;return["p-avatar p-component",{"p-avatar-image":r.image!=null,"p-avatar-circle":r.shape==="circle","p-avatar-lg":r.size==="large","p-avatar-xl":r.size==="xlarge"}]},label:"p-avatar-label",icon:"p-avatar-icon"},w=c.extend({name:"avatar",style:z,classes:S}),P={name:"BaseAvatar",extends:v,props:{label:{type:String,default:null},icon:{type:String,default:null},image:{type:String,default:null},size:{type:String,default:"normal"},shape:{type:String,default:"square"},ariaLabelledby:{type:String,default:null},ariaLabel:{type:String,default:null}},style:w,provide:function(){return{$pcAvatar:this,$parentInstance:this}}};function o(a){"@babel/helpers - typeof";return o=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(t){return typeof t}:function(t){return t&&typeof Symbol=="function"&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},o(a)}function p(a,t,r){return(t=k(t))in a?Object.defineProperty(a,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):a[t]=r,a}function k(a){var t=E(a,"string");return o(t)=="symbol"?t:t+""}function E(a,t){if(o(a)!="object"||!a)return a;var r=a[Symbol.toPrimitive];if(r!==void 0){var s=r.call(a,t);if(o(s)!="object")return s;throw new TypeError("@@toPrimitive must return a primitive value.")}return(t==="string"?String:Number)(a)}var B={name:"Avatar",extends:P,inheritAttrs:!1,emits:["error"],methods:{onError:function(t){this.$emit("error",t)}},computed:{dataP:function(){return d(p(p({},this.shape,this.shape),this.size,this.size))}}},x=["aria-labelledby","aria-label","data-p"],A=["data-p"],L=["data-p"],$=["src","alt","data-p"];function j(a,t,r,s,C,e){return i(),n("div",l({class:a.cx("root"),"aria-labelledby":a.ariaLabelledby,"aria-label":a.ariaLabel},a.ptmi("root"),{"data-p":e.dataP}),[g(a.$slots,"default",{},function(){return[a.label?(i(),n("span",l({key:0,class:a.cx("label")},a.ptm("label"),{"data-p":e.dataP}),y(a.label),17,A)):a.$slots.icon?(i(),f(h(a.$slots.icon),{key:1,class:b(a.cx("icon"))},null,8,["class"])):a.icon?(i(),n("span",l({key:2,class:[a.cx("icon"),a.icon]},a.ptm("icon"),{"data-p":e.dataP}),null,16,L)):a.image?(i(),n("img",l({key:3,src:a.image,alt:a.ariaLabel,onError:t[0]||(t[0]=function(){return e.onError&&e.onError.apply(e,arguments)})},a.ptm("image"),{"data-p":e.dataP}),null,16,$)):m("",!0)]})],16,x)}B.render=j;export{B as default};
