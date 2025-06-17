import{c}from"./Be1fzYNM.js";import{v as p,B as f,x as a,M as h,S as m,c as d,o as g,a as u,N as s,O as v,l as y,P as i,t as k}from"./DWUyzV_3.js";import{R as S}from"./B3MqGaNe.js";import{s as P}from"./C_hGxOta.js";import"./Ds2a9wz8.js";import"./DmfvTtO6.js";import"./B-kZlFfU.js";var B=p`
    .p-togglebutton {
        display: inline-flex;
        cursor: pointer;
        user-select: none;
        overflow: hidden;
        position: relative;
        color: dt('togglebutton.color');
        background: dt('togglebutton.background');
        border: 1px solid dt('togglebutton.border.color');
        padding: dt('togglebutton.padding');
        font-size: 1rem;
        font-family: inherit;
        font-feature-settings: inherit;
        transition:
            background dt('togglebutton.transition.duration'),
            color dt('togglebutton.transition.duration'),
            border-color dt('togglebutton.transition.duration'),
            outline-color dt('togglebutton.transition.duration'),
            box-shadow dt('togglebutton.transition.duration');
        border-radius: dt('togglebutton.border.radius');
        outline-color: transparent;
        font-weight: dt('togglebutton.font.weight');
    }

    .p-togglebutton-content {
        display: inline-flex;
        flex: 1 1 auto;
        align-items: center;
        justify-content: center;
        gap: dt('togglebutton.gap');
        padding: dt('togglebutton.content.padding');
        background: transparent;
        border-radius: dt('togglebutton.content.border.radius');
        transition:
            background dt('togglebutton.transition.duration'),
            color dt('togglebutton.transition.duration'),
            border-color dt('togglebutton.transition.duration'),
            outline-color dt('togglebutton.transition.duration'),
            box-shadow dt('togglebutton.transition.duration');
    }

    .p-togglebutton:not(:disabled):not(.p-togglebutton-checked):hover {
        background: dt('togglebutton.hover.background');
        color: dt('togglebutton.hover.color');
    }

    .p-togglebutton.p-togglebutton-checked {
        background: dt('togglebutton.checked.background');
        border-color: dt('togglebutton.checked.border.color');
        color: dt('togglebutton.checked.color');
    }

    .p-togglebutton-checked .p-togglebutton-content {
        background: dt('togglebutton.content.checked.background');
        box-shadow: dt('togglebutton.content.checked.shadow');
    }

    .p-togglebutton:focus-visible {
        box-shadow: dt('togglebutton.focus.ring.shadow');
        outline: dt('togglebutton.focus.ring.width') dt('togglebutton.focus.ring.style') dt('togglebutton.focus.ring.color');
        outline-offset: dt('togglebutton.focus.ring.offset');
    }

    .p-togglebutton.p-invalid {
        border-color: dt('togglebutton.invalid.border.color');
    }

    .p-togglebutton:disabled {
        opacity: 1;
        cursor: default;
        background: dt('togglebutton.disabled.background');
        border-color: dt('togglebutton.disabled.border.color');
        color: dt('togglebutton.disabled.color');
    }

    .p-togglebutton-label,
    .p-togglebutton-icon {
        position: relative;
        transition: none;
    }

    .p-togglebutton-icon {
        color: dt('togglebutton.icon.color');
    }

    .p-togglebutton:not(:disabled):not(.p-togglebutton-checked):hover .p-togglebutton-icon {
        color: dt('togglebutton.icon.hover.color');
    }

    .p-togglebutton.p-togglebutton-checked .p-togglebutton-icon {
        color: dt('togglebutton.icon.checked.color');
    }

    .p-togglebutton:disabled .p-togglebutton-icon {
        color: dt('togglebutton.icon.disabled.color');
    }

    .p-togglebutton-sm {
        padding: dt('togglebutton.sm.padding');
        font-size: dt('togglebutton.sm.font.size');
    }

    .p-togglebutton-sm .p-togglebutton-content {
        padding: dt('togglebutton.content.sm.padding');
    }

    .p-togglebutton-lg {
        padding: dt('togglebutton.lg.padding');
        font-size: dt('togglebutton.lg.font.size');
    }

    .p-togglebutton-lg .p-togglebutton-content {
        padding: dt('togglebutton.content.lg.padding');
    }
`,w={root:function(o){var e=o.instance,l=o.props;return["p-togglebutton p-component",{"p-togglebutton-checked":e.active,"p-invalid":e.$invalid,"p-togglebutton-sm p-inputfield-sm":l.size==="small","p-togglebutton-lg p-inputfield-lg":l.size==="large"}]},content:"p-togglebutton-content",icon:"p-togglebutton-icon",label:"p-togglebutton-label"},L=f.extend({name:"togglebutton",style:B,classes:w}),z={name:"BaseToggleButton",extends:P,props:{onIcon:String,offIcon:String,onLabel:{type:String,default:"Yes"},offLabel:{type:String,default:"No"},iconPos:{type:String,default:"left"},readonly:{type:Boolean,default:!1},tabindex:{type:Number,default:null},ariaLabelledby:{type:String,default:null},ariaLabel:{type:String,default:null},size:{type:String,default:null}},style:L,provide:function(){return{$pcToggleButton:this,$parentInstance:this}}};function r(t){"@babel/helpers - typeof";return r=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(o){return typeof o}:function(o){return o&&typeof Symbol=="function"&&o.constructor===Symbol&&o!==Symbol.prototype?"symbol":typeof o},r(t)}function T(t,o,e){return(o=O(o))in t?Object.defineProperty(t,o,{value:e,enumerable:!0,configurable:!0,writable:!0}):t[o]=e,t}function O(t){var o=C(t,"string");return r(o)=="symbol"?o:o+""}function C(t,o){if(r(t)!="object"||!t)return t;var e=t[Symbol.toPrimitive];if(e!==void 0){var l=e.call(t,o);if(r(l)!="object")return l;throw new TypeError("@@toPrimitive must return a primitive value.")}return(o==="string"?String:Number)(t)}var I={name:"ToggleButton",extends:z,inheritAttrs:!1,emits:["change"],methods:{getPTOptions:function(o){var e=o==="root"?this.ptmi:this.ptm;return e(o,{context:{active:this.active,disabled:this.disabled}})},onChange:function(o){!this.disabled&&!this.readonly&&(this.writeValue(!this.d_value,o),this.$emit("change",o))},onBlur:function(o){var e,l;(e=(l=this.formField).onBlur)===null||e===void 0||e.call(l,o)}},computed:{active:function(){return this.d_value===!0},hasLabel:function(){return a(this.onLabel)&&a(this.offLabel)},label:function(){return this.hasLabel?this.d_value?this.onLabel:this.offLabel:" "},dataP:function(){return c(T({checked:this.active,invalid:this.$invalid},this.size,this.size))}},directives:{ripple:S}},N=["tabindex","disabled","aria-pressed","aria-label","aria-labelledby","data-p-checked","data-p-disabled","data-p"],x=["data-p"];function j(t,o,e,l,D,n){var b=h("ripple");return m((g(),d("button",i({type:"button",class:t.cx("root"),tabindex:t.tabindex,disabled:t.disabled,"aria-pressed":t.d_value,onClick:o[0]||(o[0]=function(){return n.onChange&&n.onChange.apply(n,arguments)}),onBlur:o[1]||(o[1]=function(){return n.onBlur&&n.onBlur.apply(n,arguments)})},n.getPTOptions("root"),{"aria-label":t.ariaLabel,"aria-labelledby":t.ariaLabelledby,"data-p-checked":n.active,"data-p-disabled":t.disabled,"data-p":n.dataP}),[u("span",i({class:t.cx("content")},n.getPTOptions("content"),{"data-p":n.dataP}),[s(t.$slots,"default",{},function(){return[s(t.$slots,"icon",{value:t.d_value,class:v(t.cx("icon"))},function(){return[t.onIcon||t.offIcon?(g(),d("span",i({key:0,class:[t.cx("icon"),t.d_value?t.onIcon:t.offIcon]},n.getPTOptions("icon")),null,16)):y("",!0)]}),u("span",i({class:t.cx("label")},n.getPTOptions("label")),k(n.label),17)]})],16,x)],16,N)),[[b]])}I.render=j;export{I as default};
