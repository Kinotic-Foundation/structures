import{c as i}from"./Be1fzYNM.js";import{s}from"./B-kZlFfU.js";import{v as g,B as c,J as l,x as b,c as u,o as p,N as f,d as m,t as y,P as h}from"./DWUyzV_3.js";var v=g`
    .p-badge {
        display: inline-flex;
        border-radius: dt('badge.border.radius');
        align-items: center;
        justify-content: center;
        padding: dt('badge.padding');
        background: dt('badge.primary.background');
        color: dt('badge.primary.color');
        font-size: dt('badge.font.size');
        font-weight: dt('badge.font.weight');
        min-width: dt('badge.min.width');
        height: dt('badge.height');
    }

    .p-badge-dot {
        width: dt('badge.dot.size');
        min-width: dt('badge.dot.size');
        height: dt('badge.dot.size');
        border-radius: 50%;
        padding: 0;
    }

    .p-badge-circle {
        padding: 0;
        border-radius: 50%;
    }

    .p-badge-secondary {
        background: dt('badge.secondary.background');
        color: dt('badge.secondary.color');
    }

    .p-badge-success {
        background: dt('badge.success.background');
        color: dt('badge.success.color');
    }

    .p-badge-info {
        background: dt('badge.info.background');
        color: dt('badge.info.color');
    }

    .p-badge-warn {
        background: dt('badge.warn.background');
        color: dt('badge.warn.color');
    }

    .p-badge-danger {
        background: dt('badge.danger.background');
        color: dt('badge.danger.color');
    }

    .p-badge-contrast {
        background: dt('badge.contrast.background');
        color: dt('badge.contrast.color');
    }

    .p-badge-sm {
        font-size: dt('badge.sm.font.size');
        min-width: dt('badge.sm.min.width');
        height: dt('badge.sm.height');
    }

    .p-badge-lg {
        font-size: dt('badge.lg.font.size');
        min-width: dt('badge.lg.min.width');
        height: dt('badge.lg.height');
    }

    .p-badge-xl {
        font-size: dt('badge.xl.font.size');
        min-width: dt('badge.xl.min.width');
        height: dt('badge.xl.height');
    }
`,w={root:function(t){var a=t.props,r=t.instance;return["p-badge p-component",{"p-badge-circle":b(a.value)&&String(a.value).length===1,"p-badge-dot":l(a.value)&&!r.$slots.default,"p-badge-sm":a.size==="small","p-badge-lg":a.size==="large","p-badge-xl":a.size==="xlarge","p-badge-info":a.severity==="info","p-badge-success":a.severity==="success","p-badge-warn":a.severity==="warn","p-badge-danger":a.severity==="danger","p-badge-secondary":a.severity==="secondary","p-badge-contrast":a.severity==="contrast"}]}},z=c.extend({name:"badge",style:v,classes:w}),k={name:"BaseBadge",extends:s,props:{value:{type:[String,Number],default:null},severity:{type:String,default:null},size:{type:String,default:null}},style:z,provide:function(){return{$pcBadge:this,$parentInstance:this}}};function d(e){"@babel/helpers - typeof";return d=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(t){return typeof t}:function(t){return t&&typeof Symbol=="function"&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},d(e)}function o(e,t,a){return(t=S(t))in e?Object.defineProperty(e,t,{value:a,enumerable:!0,configurable:!0,writable:!0}):e[t]=a,e}function S(e){var t=x(e,"string");return d(t)=="symbol"?t:t+""}function x(e,t){if(d(e)!="object"||!e)return e;var a=e[Symbol.toPrimitive];if(a!==void 0){var r=a.call(e,t);if(d(r)!="object")return r;throw new TypeError("@@toPrimitive must return a primitive value.")}return(t==="string"?String:Number)(e)}var P={name:"Badge",extends:k,inheritAttrs:!1,computed:{dataP:function(){return i(o(o({circle:this.value!=null&&String(this.value).length===1,empty:this.value==null&&!this.$slots.default},this.severity,this.severity),this.size,this.size))}}},$=["data-p"];function B(e,t,a,r,N,n){return p(),u("span",h({class:e.cx("root"),"data-p":n.dataP},e.ptmi("root")),[f(e.$slots,"default",{},function(){return[m(y(e.value),1)]})],16,$)}P.render=B;export{P as default};
