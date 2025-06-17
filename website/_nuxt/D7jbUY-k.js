import{c as k}from"./Be1fzYNM.js";import{v as x,B as f,aS as m,C as y,L as h,c as g,o as d,a as s,P as n,N as v,q as b,l as C,O as P}from"./DIVREvd8.js";import{s as S}from"./DwKHZOCK.js";import{s as w}from"./CP3l7-vJ.js";import{s as z}from"./jS7rQ_kC.js";import"./fNKMJjk8.js";import"./BOp_CX-A.js";import"./DGB91fzl.js";var B=x`
    .p-checkbox {
        position: relative;
        display: inline-flex;
        user-select: none;
        vertical-align: bottom;
        width: dt('checkbox.width');
        height: dt('checkbox.height');
    }

    .p-checkbox-input {
        cursor: pointer;
        appearance: none;
        position: absolute;
        inset-block-start: 0;
        inset-inline-start: 0;
        width: 100%;
        height: 100%;
        padding: 0;
        margin: 0;
        opacity: 0;
        z-index: 1;
        outline: 0 none;
        border: 1px solid transparent;
        border-radius: dt('checkbox.border.radius');
    }

    .p-checkbox-box {
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: dt('checkbox.border.radius');
        border: 1px solid dt('checkbox.border.color');
        background: dt('checkbox.background');
        width: dt('checkbox.width');
        height: dt('checkbox.height');
        transition:
            background dt('checkbox.transition.duration'),
            color dt('checkbox.transition.duration'),
            border-color dt('checkbox.transition.duration'),
            box-shadow dt('checkbox.transition.duration'),
            outline-color dt('checkbox.transition.duration');
        outline-color: transparent;
        box-shadow: dt('checkbox.shadow');
    }

    .p-checkbox-icon {
        transition-duration: dt('checkbox.transition.duration');
        color: dt('checkbox.icon.color');
        font-size: dt('checkbox.icon.size');
        width: dt('checkbox.icon.size');
        height: dt('checkbox.icon.size');
    }

    .p-checkbox:not(.p-disabled):has(.p-checkbox-input:hover) .p-checkbox-box {
        border-color: dt('checkbox.hover.border.color');
    }

    .p-checkbox-checked .p-checkbox-box {
        border-color: dt('checkbox.checked.border.color');
        background: dt('checkbox.checked.background');
    }

    .p-checkbox-checked .p-checkbox-icon {
        color: dt('checkbox.icon.checked.color');
    }

    .p-checkbox-checked:not(.p-disabled):has(.p-checkbox-input:hover) .p-checkbox-box {
        background: dt('checkbox.checked.hover.background');
        border-color: dt('checkbox.checked.hover.border.color');
    }

    .p-checkbox-checked:not(.p-disabled):has(.p-checkbox-input:hover) .p-checkbox-icon {
        color: dt('checkbox.icon.checked.hover.color');
    }

    .p-checkbox:not(.p-disabled):has(.p-checkbox-input:focus-visible) .p-checkbox-box {
        border-color: dt('checkbox.focus.border.color');
        box-shadow: dt('checkbox.focus.ring.shadow');
        outline: dt('checkbox.focus.ring.width') dt('checkbox.focus.ring.style') dt('checkbox.focus.ring.color');
        outline-offset: dt('checkbox.focus.ring.offset');
    }

    .p-checkbox-checked:not(.p-disabled):has(.p-checkbox-input:focus-visible) .p-checkbox-box {
        border-color: dt('checkbox.checked.focus.border.color');
    }

    .p-checkbox.p-invalid > .p-checkbox-box {
        border-color: dt('checkbox.invalid.border.color');
    }

    .p-checkbox.p-variant-filled .p-checkbox-box {
        background: dt('checkbox.filled.background');
    }

    .p-checkbox-checked.p-variant-filled .p-checkbox-box {
        background: dt('checkbox.checked.background');
    }

    .p-checkbox-checked.p-variant-filled:not(.p-disabled):has(.p-checkbox-input:hover) .p-checkbox-box {
        background: dt('checkbox.checked.hover.background');
    }

    .p-checkbox.p-disabled {
        opacity: 1;
    }

    .p-checkbox.p-disabled .p-checkbox-box {
        background: dt('checkbox.disabled.background');
        border-color: dt('checkbox.checked.disabled.border.color');
    }

    .p-checkbox.p-disabled .p-checkbox-box .p-checkbox-icon {
        color: dt('checkbox.icon.disabled.color');
    }

    .p-checkbox-sm,
    .p-checkbox-sm .p-checkbox-box {
        width: dt('checkbox.sm.width');
        height: dt('checkbox.sm.height');
    }

    .p-checkbox-sm .p-checkbox-icon {
        font-size: dt('checkbox.icon.sm.size');
        width: dt('checkbox.icon.sm.size');
        height: dt('checkbox.icon.sm.size');
    }

    .p-checkbox-lg,
    .p-checkbox-lg .p-checkbox-box {
        width: dt('checkbox.lg.width');
        height: dt('checkbox.lg.height');
    }

    .p-checkbox-lg .p-checkbox-icon {
        font-size: dt('checkbox.icon.lg.size');
        width: dt('checkbox.icon.lg.size');
        height: dt('checkbox.icon.lg.size');
    }
`,_={root:function(o){var t=o.instance,i=o.props;return["p-checkbox p-component",{"p-checkbox-checked":t.checked,"p-disabled":i.disabled,"p-invalid":t.$pcCheckboxGroup?t.$pcCheckboxGroup.$invalid:t.$invalid,"p-variant-filled":t.$variant==="filled","p-checkbox-sm p-inputfield-sm":i.size==="small","p-checkbox-lg p-inputfield-lg":i.size==="large"}]},box:"p-checkbox-box",input:"p-checkbox-input",icon:"p-checkbox-icon"},I=f.extend({name:"checkbox",style:B,classes:_}),A={name:"BaseCheckbox",extends:z,props:{value:null,binary:Boolean,indeterminate:{type:Boolean,default:!1},trueValue:{type:null,default:!0},falseValue:{type:null,default:!1},readonly:{type:Boolean,default:!1},required:{type:Boolean,default:!1},tabindex:{type:Number,default:null},inputId:{type:String,default:null},inputClass:{type:[String,Object],default:null},inputStyle:{type:Object,default:null},ariaLabelledby:{type:String,default:null},ariaLabel:{type:String,default:null}},style:I,provide:function(){return{$pcCheckbox:this,$parentInstance:this}}};function a(e){"@babel/helpers - typeof";return a=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(o){return typeof o}:function(o){return o&&typeof Symbol=="function"&&o.constructor===Symbol&&o!==Symbol.prototype?"symbol":typeof o},a(e)}function O(e,o,t){return(o=T(o))in e?Object.defineProperty(e,o,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[o]=t,e}function T(e){var o=G(e,"string");return a(o)=="symbol"?o:o+""}function G(e,o){if(a(e)!="object"||!e)return e;var t=e[Symbol.toPrimitive];if(t!==void 0){var i=t.call(e,o);if(a(i)!="object")return i;throw new TypeError("@@toPrimitive must return a primitive value.")}return(o==="string"?String:Number)(e)}function N(e){return q(e)||$(e)||F(e)||j()}function j(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function F(e,o){if(e){if(typeof e=="string")return l(e,o);var t={}.toString.call(e).slice(8,-1);return t==="Object"&&e.constructor&&(t=e.constructor.name),t==="Map"||t==="Set"?Array.from(e):t==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(t)?l(e,o):void 0}}function $(e){if(typeof Symbol<"u"&&e[Symbol.iterator]!=null||e["@@iterator"]!=null)return Array.from(e)}function q(e){if(Array.isArray(e))return l(e)}function l(e,o){(o==null||o>e.length)&&(o=e.length);for(var t=0,i=Array(o);t<o;t++)i[t]=e[t];return i}var L={name:"Checkbox",extends:A,inheritAttrs:!1,emits:["change","focus","blur","update:indeterminate"],inject:{$pcCheckboxGroup:{default:void 0}},data:function(){return{d_indeterminate:this.indeterminate}},watch:{indeterminate:function(o){this.d_indeterminate=o}},methods:{getPTOptions:function(o){var t=o==="root"?this.ptmi:this.ptm;return t(o,{context:{checked:this.checked,indeterminate:this.d_indeterminate,disabled:this.disabled}})},onChange:function(o){var t=this;if(!this.disabled&&!this.readonly){var i=this.$pcCheckboxGroup?this.$pcCheckboxGroup.d_value:this.d_value,r;this.binary?r=this.d_indeterminate?this.trueValue:this.checked?this.falseValue:this.trueValue:this.checked||this.d_indeterminate?r=i.filter(function(c){return!y(c,t.value)}):r=i?[].concat(N(i),[this.value]):[this.value],this.d_indeterminate&&(this.d_indeterminate=!1,this.$emit("update:indeterminate",this.d_indeterminate)),this.$pcCheckboxGroup?this.$pcCheckboxGroup.writeValue(r,o):this.writeValue(r,o),this.$emit("change",o)}},onFocus:function(o){this.$emit("focus",o)},onBlur:function(o){var t,i;this.$emit("blur",o),(t=(i=this.formField).onBlur)===null||t===void 0||t.call(i,o)}},computed:{groupName:function(){return this.$pcCheckboxGroup?this.$pcCheckboxGroup.groupName:this.$formName},checked:function(){var o=this.$pcCheckboxGroup?this.$pcCheckboxGroup.d_value:this.d_value;return this.d_indeterminate?!1:this.binary?o===this.trueValue:m(this.value,o)},dataP:function(){return k(O({invalid:this.$invalid,checked:this.checked,disabled:this.disabled,filled:this.$variant==="filled"},this.size,this.size))}},components:{CheckIcon:S,MinusIcon:w}},V=["data-p-checked","data-p-indeterminate","data-p-disabled","data-p"],E=["id","value","name","checked","tabindex","disabled","readonly","required","aria-labelledby","aria-label","aria-invalid","aria-checked"],H=["data-p"];function K(e,o,t,i,r,c){var u=h("CheckIcon"),p=h("MinusIcon");return d(),g("div",n({class:e.cx("root")},c.getPTOptions("root"),{"data-p-checked":c.checked,"data-p-indeterminate":r.d_indeterminate||void 0,"data-p-disabled":e.disabled,"data-p":c.dataP}),[s("input",n({id:e.inputId,type:"checkbox",class:[e.cx("input"),e.inputClass],style:e.inputStyle,value:e.value,name:c.groupName,checked:c.checked,tabindex:e.tabindex,disabled:e.disabled,readonly:e.readonly,required:e.required,"aria-labelledby":e.ariaLabelledby,"aria-label":e.ariaLabel,"aria-invalid":e.invalid||void 0,"aria-checked":r.d_indeterminate?"mixed":void 0,onFocus:o[0]||(o[0]=function(){return c.onFocus&&c.onFocus.apply(c,arguments)}),onBlur:o[1]||(o[1]=function(){return c.onBlur&&c.onBlur.apply(c,arguments)}),onChange:o[2]||(o[2]=function(){return c.onChange&&c.onChange.apply(c,arguments)})},c.getPTOptions("input")),null,16,E),s("div",n({class:e.cx("box")},c.getPTOptions("box"),{"data-p":c.dataP}),[v(e.$slots,"icon",{checked:c.checked,indeterminate:r.d_indeterminate,class:P(e.cx("icon")),dataP:c.dataP},function(){return[c.checked?(d(),b(u,n({key:0,class:e.cx("icon")},c.getPTOptions("icon"),{"data-p":c.dataP}),null,16,["class","data-p"])):r.d_indeterminate?(d(),b(p,n({key:1,class:e.cx("icon")},c.getPTOptions("icon"),{"data-p":c.dataP}),null,16,["class","data-p"])):C("",!0)]})],16,H)],16,V)}L.render=K;export{L as default};
