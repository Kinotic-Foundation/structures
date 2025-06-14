import{c as a}from"./Be1fzYNM.js";import{s}from"./BZ7rLBd_.js";import{v as l,B as p,c as u,o as d,P as c}from"./CwV9-nPG.js";var f=l`
    .p-skeleton {
        overflow: hidden;
        background: dt('skeleton.background');
        border-radius: dt('skeleton.border.radius');
    }

    .p-skeleton::after {
        content: '';
        animation: p-skeleton-animation 1.2s infinite;
        height: 100%;
        left: 0;
        position: absolute;
        right: 0;
        top: 0;
        transform: translateX(-100%);
        z-index: 1;
        background: linear-gradient(90deg, rgba(255, 255, 255, 0), dt('skeleton.animation.background'), rgba(255, 255, 255, 0));
    }

    [dir='rtl'] .p-skeleton::after {
        animation-name: p-skeleton-animation-rtl;
    }

    .p-skeleton-circle {
        border-radius: 50%;
    }

    .p-skeleton-animation-none::after {
        animation: none;
    }

    @keyframes p-skeleton-animation {
        from {
            transform: translateX(-100%);
        }
        to {
            transform: translateX(100%);
        }
    }

    @keyframes p-skeleton-animation-rtl {
        from {
            transform: translateX(100%);
        }
        to {
            transform: translateX(-100%);
        }
    }
`,m={root:{position:"relative"}},y={root:function(t){var r=t.props;return["p-skeleton p-component",{"p-skeleton-circle":r.shape==="circle","p-skeleton-animation-none":r.animation==="none"}]}},h=p.extend({name:"skeleton",style:f,classes:y,inlineStyles:m}),b={name:"BaseSkeleton",extends:s,props:{shape:{type:String,default:"rectangle"},size:{type:String,default:null},width:{type:String,default:"100%"},height:{type:String,default:"1rem"},borderRadius:{type:String,default:null},animation:{type:String,default:"wave"}},style:h,provide:function(){return{$pcSkeleton:this,$parentInstance:this}}};function n(e){"@babel/helpers - typeof";return n=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(t){return typeof t}:function(t){return t&&typeof Symbol=="function"&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},n(e)}function k(e,t,r){return(t=g(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function g(e){var t=v(e,"string");return n(t)=="symbol"?t:t+""}function v(e,t){if(n(e)!="object"||!e)return e;var r=e[Symbol.toPrimitive];if(r!==void 0){var o=r.call(e,t);if(n(o)!="object")return o;throw new TypeError("@@toPrimitive must return a primitive value.")}return(t==="string"?String:Number)(e)}var S={name:"Skeleton",extends:b,inheritAttrs:!1,computed:{containerStyle:function(){return this.size?{width:this.size,height:this.size,borderRadius:this.borderRadius}:{width:this.width,height:this.height,borderRadius:this.borderRadius}},dataP:function(){return a(k({},this.shape,this.shape))}}},P=["data-p"];function w(e,t,r,o,$,i){return d(),u("div",c({class:e.cx("root"),style:[e.sx("root"),i.containerStyle],"aria-hidden":"true"},e.ptmi("root"),{"data-p":i.dataP}),null,16,P)}S.render=w;export{S as default};
