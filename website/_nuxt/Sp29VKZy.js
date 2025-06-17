import O from"./D2pJApHx.js";import{s as k}from"./B-kZlFfU.js";import{v as C,B as x,L as V,c as f,o as h,a as m,F as S,s as B,P as l,N as v,b as A,w as P,O as b}from"./DWUyzV_3.js";import"./Be1fzYNM.js";import"./x_TrJ5H1.js";import"./A4PA4AXe.js";var j=C`
    .p-inputchips {
        display: inline-flex;
    }

    .p-inputchips-input {
        margin: 0;
        list-style-type: none;
        cursor: text;
        overflow: hidden;
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        padding: calc(dt('inputchips.padding.y') / 2) dt('inputchips.padding.x');
        gap: calc(dt('inputchips.padding.y') / 2);
        color: dt('inputchips.color');
        background: dt('inputchips.background');
        border: 1px solid dt('inputchips.border.color');
        border-radius: dt('inputchips.border.radius');
        width: 100%;
        transition:
            background dt('inputchips.transition.duration'),
            color dt('inputchips.transition.duration'),
            border-color dt('inputchips.transition.duration'),
            outline-color dt('inputchips.transition.duration'),
            box-shadow dt('inputchips.transition.duration');
        outline-color: transparent;
        box-shadow: dt('inputchips.shadow');
    }

    .p-inputchips:not(.p-disabled):hover .p-inputchips-input {
        border-color: dt('inputchips.hover.border.color');
    }

    .p-inputchips:not(.p-disabled).p-focus .p-inputchips-input {
        border-color: dt('inputchips.focus.border.color');
        box-shadow: dt('inputchips.focus.ring.shadow');
        outline: dt('inputchips.focus.ring.width') dt('inputchips.focus.ring.style') dt('inputchips.focus.ring.color');
        outline-offset: dt('inputchips.focus.ring.offset');
    }

    .p-inputchips.p-invalid .p-inputchips-input {
        border-color: dt('inputchips.invalid.border.color');
    }

    .p-variant-filled.p-inputchips-input {
        background: dt('inputchips.filled.background');
    }

    .p-inputchips:not(.p-disabled).p-focus .p-variant-filled.p-inputchips-input {
        background: dt('inputchips.filled.focus.background');
    }

    .p-inputchips.p-disabled .p-inputchips-input {
        opacity: 1;
        background: dt('inputchips.disabled.background');
        color: dt('inputchips.disabled.color');
    }

    .p-inputchips-chip.p-chip {
        padding-top: calc(dt('inputchips.padding.y') / 2);
        padding-bottom: calc(dt('inputchips.padding.y') / 2);
        border-radius: dt('inputchips.chip.border.radius');
        transition:
            background dt('inputchips.transition.duration'),
            color dt('inputchips.transition.duration');
    }

    .p-inputchips-chip-item.p-focus .p-inputchips-chip {
        background: dt('inputchips.chip.focus.background');
        color: dt('inputchips.chip.focus.color');
    }

    .p-inputchips-input:has(.p-inputchips-chip) {
        padding-left: calc(dt('inputchips.padding.y') / 2);
        padding-right: calc(dt('inputchips.padding.y') / 2);
    }

    .p-inputchips-input-item {
        flex: 1 1 auto;
        display: inline-flex;
        padding-top: calc(dt('inputchips.padding.y') / 2);
        padding-bottom: calc(dt('inputchips.padding.y') / 2);
    }

    .p-inputchips-input-item input {
        border: 0 none;
        outline: 0 none;
        background: transparent;
        margin: 0;
        padding: 0;
        box-shadow: none;
        border-radius: 0;
        width: 100%;
        font-family: inherit;
        font-feature-settings: inherit;
        font-size: 1rem;
        color: inherit;
    }

    .p-inputchips-input-item input::placeholder {
        color: dt('inputchips.placeholder.color');
    }
`,K={root:function(e){var n=e.instance,i=e.props;return["p-inputchips p-component p-inputwrapper",{"p-disabled":i.disabled,"p-invalid":i.invalid,"p-focus":n.focused,"p-inputwrapper-filled":i.modelValue&&i.modelValue.length||n.inputValue&&n.inputValue.length,"p-inputwrapper-focus":n.focused}]},input:function(e){var n=e.props,i=e.instance;return["p-inputchips-input",{"p-variant-filled":n.variant?n.variant==="filled":i.$primevue.config.inputStyle==="filled"||i.$primevue.config.inputVariant==="filled"}]},chipItem:function(e){var n=e.state,i=e.index;return["p-inputchips-chip-item",{"p-focus":n.focusedIndex===i}]},pcChip:"p-inputchips-chip",chipIcon:"p-inputchips-chip-icon",inputItem:"p-inputchips-input-item"},D=x.extend({name:"inputchips",style:j,classes:K}),F={name:"BaseInputChips",extends:k,props:{modelValue:{type:Array,default:null},max:{type:Number,default:null},separator:{type:[String,Object],default:null},addOnBlur:{type:Boolean,default:null},allowDuplicate:{type:Boolean,default:!0},placeholder:{type:String,default:null},variant:{type:String,default:null},invalid:{type:Boolean,default:!1},disabled:{type:Boolean,default:!1},inputId:{type:String,default:null},inputClass:{type:[String,Object],default:null},inputStyle:{type:Object,default:null},inputProps:{type:null,default:null},removeTokenIcon:{type:String,default:void 0},chipIcon:{type:String,default:void 0},ariaLabelledby:{type:String,default:null},ariaLabel:{type:String,default:null}},style:D,provide:function(){return{$pcInputChips:this,$parentInstance:this}}};function c(t){return R(t)||T(t)||E(t)||L()}function L(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function E(t,e){if(t){if(typeof t=="string")return y(t,e);var n={}.toString.call(t).slice(8,-1);return n==="Object"&&t.constructor&&(n=t.constructor.name),n==="Map"||n==="Set"?Array.from(t):n==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?y(t,e):void 0}}function T(t){if(typeof Symbol<"u"&&t[Symbol.iterator]!=null||t["@@iterator"]!=null)return Array.from(t)}function R(t){if(Array.isArray(t))return y(t)}function y(t,e){(e==null||e>t.length)&&(e=t.length);for(var n=0,i=Array(e);n<e;n++)i[n]=t[n];return i}var N={name:"InputChips",extends:F,inheritAttrs:!1,emits:["update:modelValue","add","remove","focus","blur"],data:function(){return{inputValue:null,focused:!1,focusedIndex:null}},mounted:function(){console.warn("Deprecated since v4. Use AutoComplete component instead with its typeahead property.")},methods:{onWrapperClick:function(){this.$refs.input.focus()},onInput:function(e){this.inputValue=e.target.value,this.focusedIndex=null},onFocus:function(e){this.focused=!0,this.focusedIndex=null,this.$emit("focus",e)},onBlur:function(e){this.focused=!1,this.focusedIndex=null,this.addOnBlur&&this.addItem(e,e.target.value,!1),this.$emit("blur",e)},onKeyDown:function(e){var n=e.target.value;switch(e.code){case"Backspace":n.length===0&&this.modelValue&&this.modelValue.length>0&&(this.focusedIndex!==null?this.removeItem(e,this.focusedIndex):this.removeItem(e,this.modelValue.length-1));break;case"Enter":case"NumpadEnter":n&&n.trim().length&&!this.maxedOut&&this.addItem(e,n,!0);break;case"ArrowLeft":n.length===0&&this.modelValue&&this.modelValue.length>0&&this.$refs.container.focus();break;case"ArrowRight":e.stopPropagation();break;default:this.separator&&(this.separator===e.key||e.key.match(this.separator))&&this.addItem(e,n,!0);break}},onPaste:function(e){var n=this;if(this.separator){var i=this.separator.replace("\\n",`
`).replace("\\r","\r").replace("\\t","	"),r=(e.clipboardData||window.clipboardData).getData("Text");if(r){var o=this.modelValue||[],p=r.split(i);p=p.filter(function(a){return n.allowDuplicate||o.indexOf(a)===-1}),o=[].concat(c(o),c(p)),this.updateModel(e,o,!0)}}},onContainerFocus:function(){this.focused=!0},onContainerBlur:function(){this.focusedIndex=-1,this.focused=!1},onContainerKeyDown:function(e){switch(e.code){case"ArrowLeft":this.onArrowLeftKeyOn(e);break;case"ArrowRight":this.onArrowRightKeyOn(e);break;case"Backspace":this.onBackspaceKeyOn(e);break}},onArrowLeftKeyOn:function(){this.inputValue.length===0&&this.modelValue&&this.modelValue.length>0&&(this.focusedIndex=this.focusedIndex===null?this.modelValue.length-1:this.focusedIndex-1,this.focusedIndex<0&&(this.focusedIndex=0))},onArrowRightKeyOn:function(){this.inputValue.length===0&&this.modelValue&&this.modelValue.length>0&&(this.focusedIndex===this.modelValue.length-1?(this.focusedIndex=null,this.$refs.input.focus()):this.focusedIndex++)},onBackspaceKeyOn:function(e){this.focusedIndex!==null&&this.removeItem(e,this.focusedIndex)},updateModel:function(e,n,i){var r=this;this.$emit("update:modelValue",n),this.$emit("add",{originalEvent:e,value:n}),this.$refs.input.value="",this.inputValue="",setTimeout(function(){r.maxedOut&&(r.focused=!1)},0),i&&e.preventDefault()},addItem:function(e,n,i){if(n&&n.trim().length){var r=this.modelValue?c(this.modelValue):[];(this.allowDuplicate||r.indexOf(n)===-1)&&(r.push(n),this.updateModel(e,r,i))}},removeItem:function(e,n){if(!this.disabled){var i=c(this.modelValue),r=i.splice(n,1);this.focusedIndex=null,this.$refs.input.focus(),this.$emit("update:modelValue",i),this.$emit("remove",{originalEvent:e,value:r})}}},computed:{maxedOut:function(){return this.max&&this.modelValue&&this.max===this.modelValue.length},focusedOptionId:function(){return this.focusedIndex!==null?"".concat(this.$id,"_inputchips_item_").concat(this.focusedIndex):null}},components:{Chip:O}};function d(t){"@babel/helpers - typeof";return d=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},d(t)}function I(t,e){var n=Object.keys(t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(t);e&&(i=i.filter(function(r){return Object.getOwnPropertyDescriptor(t,r).enumerable})),n.push.apply(n,i)}return n}function w(t){for(var e=1;e<arguments.length;e++){var n=arguments[e]!=null?arguments[e]:{};e%2?I(Object(n),!0).forEach(function(i){$(t,i,n[i])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(n)):I(Object(n)).forEach(function(i){Object.defineProperty(t,i,Object.getOwnPropertyDescriptor(n,i))})}return t}function $(t,e,n){return(e=z(e))in t?Object.defineProperty(t,e,{value:n,enumerable:!0,configurable:!0,writable:!0}):t[e]=n,t}function z(t){var e=M(t,"string");return d(e)=="symbol"?e:e+""}function M(t,e){if(d(t)!="object"||!t)return t;var n=t[Symbol.toPrimitive];if(n!==void 0){var i=n.call(t,e);if(d(i)!="object")return i;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}var W=["aria-labelledby","aria-label","aria-activedescendant"],U=["id","aria-label","aria-setsize","aria-posinset","data-p-focused"],H=["id","disabled","placeholder","aria-invalid"];function q(t,e,n,i,r,o){var p=V("Chip");return h(),f("div",l({class:t.cx("root")},t.ptmi("root")),[m("ul",l({ref:"container",class:t.cx("input"),tabindex:"-1",role:"listbox","aria-orientation":"horizontal","aria-labelledby":t.ariaLabelledby,"aria-label":t.ariaLabel,"aria-activedescendant":r.focused?o.focusedOptionId:void 0,onClick:e[5]||(e[5]=function(a){return o.onWrapperClick()}),onFocus:e[6]||(e[6]=function(){return o.onContainerFocus&&o.onContainerFocus.apply(o,arguments)}),onBlur:e[7]||(e[7]=function(){return o.onContainerBlur&&o.onContainerBlur.apply(o,arguments)}),onKeydown:e[8]||(e[8]=function(){return o.onContainerKeyDown&&o.onContainerKeyDown.apply(o,arguments)})},t.ptm("input")),[(h(!0),f(S,null,B(t.modelValue,function(a,u){return h(),f("li",l({key:"".concat(u,"_").concat(a),id:t.$id+"_inputchips_item_"+u,role:"option",class:t.cx("chipItem",{index:u}),"aria-label":a,"aria-selected":!0,"aria-setsize":t.modelValue.length,"aria-posinset":u+1},{ref_for:!0},t.ptm("chipItem"),{"data-p-focused":r.focusedIndex===u}),[v(t.$slots,"chip",{class:b(t.cx("pcChip")),index:u,value:a,removeCallback:function(s){return t.removeOption(s,u)}},function(){return[A(p,{class:b(t.cx("pcChip")),label:a,removeIcon:t.chipIcon||t.removeTokenIcon,removable:"",unstyled:t.unstyled,onRemove:function(s){return o.removeItem(s,u)},pt:t.ptm("pcChip")},{removeicon:P(function(){return[v(t.$slots,t.$slots.chipicon?"chipicon":"removetokenicon",{class:b(t.cx("chipIcon")),index:u,removeCallback:function(s){return o.removeItem(s,u)}})]}),_:2},1032,["class","label","removeIcon","unstyled","onRemove","pt"])]})],16,U)}),128)),m("li",l({class:t.cx("inputItem"),role:"option"},t.ptm("inputItem")),[m("input",l({ref:"input",id:t.inputId,type:"text",class:t.inputClass,style:t.inputStyle,disabled:t.disabled||o.maxedOut,placeholder:t.placeholder,"aria-invalid":t.invalid||void 0,onFocus:e[0]||(e[0]=function(a){return o.onFocus(a)}),onBlur:e[1]||(e[1]=function(a){return o.onBlur(a)}),onInput:e[2]||(e[2]=function(){return o.onInput&&o.onInput.apply(o,arguments)}),onKeydown:e[3]||(e[3]=function(a){return o.onKeyDown(a)}),onPaste:e[4]||(e[4]=function(a){return o.onPaste(a)})},w(w({},t.inputProps),t.ptm("inputItemField"))),null,16,H)],16)],16,W)],16)}N.render=q;export{N as default};
