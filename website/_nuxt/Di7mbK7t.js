import{v as N,B as q,x as f,z as U,J as G,C as R,y as F,D as W,E as $,G as Z,H as J,I as Q,A as P,U as X,K as y,V as Y,L,M as _,c as v,o as d,F as ee,s as te,P as l,S as ie,q as O,l as x,Q as V,t as K,O as I,a as g,N as m,b as E,d as ne,w as A,T as oe}from"./DIVREvd8.js";import{Z as D}from"./DGEOgZpe.js";import{C as se}from"./DcP9kpS0.js";import{s as B}from"./BfxIQXxQ.js";import{s as ae}from"./rzT3uIO0.js";import{s as re}from"./D_f71Dqf.js";import{s as le}from"./DcKfR-gm.js";import{O as ce}from"./DtO3SA5H.js";import{s as de}from"./Dk-jlASV.js";import{s as ue}from"./jS7rQ_kC.js";import{s as pe}from"./BOp_CX-A.js";import{R as he}from"./DHxJ3ARZ.js";import"./fNKMJjk8.js";import"./DGB91fzl.js";import"./zNb_omYy.js";import"./DmfvTtO6.js";var fe=N`
    .p-cascadeselect {
        display: inline-flex;
        cursor: pointer;
        position: relative;
        user-select: none;
        background: dt('cascadeselect.background');
        border: 1px solid dt('cascadeselect.border.color');
        transition:
            background dt('cascadeselect.transition.duration'),
            color dt('cascadeselect.transition.duration'),
            border-color dt('cascadeselect.transition.duration'),
            outline-color dt('cascadeselect.transition.duration'),
            box-shadow dt('cascadeselect.transition.duration');
        border-radius: dt('cascadeselect.border.radius');
        outline-color: transparent;
        box-shadow: dt('cascadeselect.shadow');
    }

    .p-cascadeselect:not(.p-disabled):hover {
        border-color: dt('cascadeselect.hover.border.color');
    }

    .p-cascadeselect:not(.p-disabled).p-focus {
        border-color: dt('cascadeselect.focus.border.color');
        box-shadow: dt('cascadeselect.focus.ring.shadow');
        outline: dt('cascadeselect.focus.ring.width') dt('cascadeselect.focus.ring.style') dt('cascadeselect.focus.ring.color');
        outline-offset: dt('cascadeselect.focus.ring.offset');
    }

    .p-cascadeselect.p-variant-filled {
        background: dt('cascadeselect.filled.background');
    }

    .p-cascadeselect.p-variant-filled:not(.p-disabled):hover {
        background: dt('cascadeselect.filled.hover.background');
    }

    .p-cascadeselect.p-variant-filled.p-focus {
        background: dt('cascadeselect.filled.focus.background');
    }

    .p-cascadeselect.p-invalid {
        border-color: dt('cascadeselect.invalid.border.color');
    }

    .p-cascadeselect.p-disabled {
        opacity: 1;
        background: dt('cascadeselect.disabled.background');
    }

    .p-cascadeselect-dropdown {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-shrink: 0;
        background: transparent;
        color: dt('cascadeselect.dropdown.color');
        width: dt('cascadeselect.dropdown.width');
        border-start-end-radius: dt('border.radius.md');
        border-end-end-radius: dt('border.radius.md');
    }

    .p-cascadeselect-clear-icon {
        position: absolute;
        top: 50%;
        margin-top: -0.5rem;
        color: dt('cascadeselect.clear.icon.color');
        inset-inline-end: dt('cascadeselect.dropdown.width');
    }

    .p-cascadeselect-label {
        display: block;
        white-space: nowrap;
        overflow: hidden;
        flex: 1 1 auto;
        width: 1%;
        text-overflow: ellipsis;
        cursor: pointer;
        padding: dt('cascadeselect.padding.y') dt('cascadeselect.padding.x');
        background: transparent;
        border: 0 none;
        outline: 0 none;
    }

    .p-cascadeselect-label.p-placeholder {
        color: dt('cascadeselect.placeholder.color');
    }

    .p-cascadeselect.p-invalid .p-cascadeselect-label.p-placeholder {
        color: dt('cascadeselect.invalid.placeholder.color');
    }

    .p-cascadeselect.p-disabled .p-cascadeselect-label {
        color: dt('cascadeselect.disabled.color');
    }

    .p-cascadeselect-label-empty {
        overflow: hidden;
        visibility: hidden;
    }

    .p-cascadeselect-fluid {
        display: flex;
    }

    .p-cascadeselect-fluid .p-cascadeselect-label {
        width: 1%;
    }

    .p-cascadeselect-overlay {
        background: dt('cascadeselect.overlay.background');
        color: dt('cascadeselect.overlay.color');
        border: 1px solid dt('cascadeselect.overlay.border.color');
        border-radius: dt('cascadeselect.overlay.border.radius');
        box-shadow: dt('cascadeselect.overlay.shadow');
    }

    .p-cascadeselect .p-cascadeselect-overlay {
        min-width: 100%;
    }

    .p-cascadeselect-option-list {
        display: none;
        min-width: 100%;
        position: absolute;
        z-index: 1;
    }

    .p-cascadeselect-list {
        min-width: 100%;
        margin: 0;
        padding: 0;
        list-style-type: none;
        padding: dt('cascadeselect.list.padding');
        display: flex;
        flex-direction: column;
        gap: dt('cascadeselect.list.gap');
    }

    .p-cascadeselect-option {
        cursor: pointer;
        font-weight: normal;
        white-space: nowrap;
        border: 0 none;
        color: dt('cascadeselect.option.color');
        background: transparent;
        border-radius: dt('cascadeselect.option.border.radius');
    }

    .p-cascadeselect-option-active {
        overflow: visible;
    }

    .p-cascadeselect-option-active > .p-cascadeselect-option-content {
        background: dt('cascadeselect.option.focus.background');
        color: dt('cascadeselect.option.focus.color');
    }

    .p-cascadeselect-option:not(.p-cascadeselect-option-selected):not(.p-disabled).p-focus > .p-cascadeselect-option-content {
        background: dt('cascadeselect.option.focus.background');
        color: dt('cascadeselect.option.focus.color');
    }

    .p-cascadeselect-option:not(.p-cascadeselect-option-selected):not(.p-disabled).p-focus > .p-cascadeselect-option-content > .p-cascadeselect-group-icon-container > .p-cascadeselect-group-icon {
        color: dt('cascadeselect.option.icon.focus.color');
    }

    .p-cascadeselect-option-selected > .p-cascadeselect-option-content {
        background: dt('cascadeselect.option.selected.background');
        color: dt('cascadeselect.option.selected.color');
    }

    .p-cascadeselect-option-selected.p-focus > .p-cascadeselect-option-content {
        background: dt('cascadeselect.option.selected.focus.background');
        color: dt('cascadeselect.option.selected.focus.color');
    }

    .p-cascadeselect-option-active > .p-cascadeselect-option-list {
        inset-inline-start: 100%;
        inset-block-start: 0;
    }

    .p-cascadeselect-option-content {
        display: flex;
        align-items: center;
        justify-content: space-between;
        overflow: hidden;
        position: relative;
        padding: dt('cascadeselect.option.padding');
        border-radius: dt('cascadeselect.option.border.radius');
        transition:
            background dt('cascadeselect.transition.duration'),
            color dt('cascadeselect.transition.duration'),
            border-color dt('cascadeselect.transition.duration'),
            box-shadow dt('cascadeselect.transition.duration'),
            outline-color dt('cascadeselect.transition.duration');
    }

    .p-cascadeselect-group-icon {
        font-size: dt('cascadeselect.option.icon.size');
        width: dt('cascadeselect.option.icon.size');
        height: dt('cascadeselect.option.icon.size');
        color: dt('cascadeselect.option.icon.color');
    }

    .p-cascadeselect-group-icon:dir(rtl) {
        transform: rotate(180deg);
    }

    .p-cascadeselect-mobile-active .p-cascadeselect-option-list {
        position: static;
        box-shadow: none;
        border: 0 none;
        padding-inline-start: dt('tieredmenu.submenu.mobile.indent');
        padding-inline-end: 0;
    }

    .p-cascadeselect-mobile-active .p-cascadeselect-group-icon {
        transition: transform 0.2s;
        transform: rotate(90deg);
    }

    .p-cascadeselect-mobile-active .p-cascadeselect-option-active > .p-cascadeselect-option-content .p-cascadeselect-group-icon {
        transform: rotate(-90deg);
    }

    .p-cascadeselect-sm .p-cascadeselect-label {
        font-size: dt('cascadeselect.sm.font.size');
        padding-block: dt('cascadeselect.sm.padding.y');
        padding-inline: dt('cascadeselect.sm.padding.x');
    }

    .p-cascadeselect-sm .p-cascadeselect-dropdown .p-icon {
        font-size: dt('cascadeselect.sm.font.size');
        width: dt('cascadeselect.sm.font.size');
        height: dt('cascadeselect.sm.font.size');
    }

    .p-cascadeselect-lg .p-cascadeselect-label {
        font-size: dt('cascadeselect.lg.font.size');
        padding-block: dt('cascadeselect.lg.padding.y');
        padding-inline: dt('cascadeselect.lg.padding.x');
    }

    .p-cascadeselect-lg .p-cascadeselect-dropdown .p-icon {
        font-size: dt('cascadeselect.lg.font.size');
        width: dt('cascadeselect.lg.font.size');
        height: dt('cascadeselect.lg.font.size');
    }
`,ve={root:function(e){var i=e.props;return{position:i.appendTo==="self"?"relative":void 0}}},ye={root:function(e){var i=e.instance,n=e.props;return["p-cascadeselect p-component p-inputwrapper",{"p-cascadeselect-mobile":i.queryMatches,"p-disabled":n.disabled,"p-invalid":i.$invalid,"p-variant-filled":i.$variant==="filled","p-focus":i.focused,"p-inputwrapper-filled":i.$filled,"p-inputwrapper-focus":i.focused||i.overlayVisible,"p-cascadeselect-open":i.overlayVisible,"p-cascadeselect-fluid":i.$fluid,"p-cascadeselect-sm p-inputfield-sm":n.size==="small","p-cascadeselect-lg p-inputfield-lg":n.size==="large"}]},label:function(e){var i,n=e.instance,s=e.props;return["p-cascadeselect-label",{"p-placeholder":n.label===s.placeholder,"p-cascadeselect-label-empty":!n.$slots.value&&(n.label==="p-emptylabel"||((i=n.label)===null||i===void 0?void 0:i.length)===0)}]},clearIcon:"p-cascadeselect-clear-icon",dropdown:"p-cascadeselect-dropdown",loadingIcon:"p-cascadeselect-loading-icon",dropdownIcon:"p-cascadeselect-dropdown-icon",overlay:function(e){var i=e.instance;return["p-cascadeselect-overlay p-component",{"p-cascadeselect-mobile-active":i.queryMatches}]},listContainer:"p-cascadeselect-list-container",list:"p-cascadeselect-list",option:function(e){var i=e.instance,n=e.processedOption;return["p-cascadeselect-option",{"p-cascadeselect-option-active":i.isOptionActive(n),"p-cascadeselect-option-selected":i.isOptionSelected(n),"p-focus":i.isOptionFocused(n),"p-disabled":i.isOptionDisabled(n)}]},optionContent:"p-cascadeselect-option-content",optionText:"p-cascadeselect-option-text",groupIconContainer:"p-cascadeselect-group-icon-container",groupIcon:"p-cascadeselect-group-icon",optionList:"p-cascadeselect-overlay p-cascadeselect-option-list"},be=q.extend({name:"cascadeselect",style:fe,classes:ye,inlineStyles:ve}),ge={name:"BaseCascadeSelect",extends:ue,props:{options:Array,optionLabel:null,optionValue:null,optionDisabled:null,optionGroupLabel:null,optionGroupChildren:null,placeholder:String,breakpoint:{type:String,default:"960px"},dataKey:null,showClear:{type:Boolean,default:!1},clearIcon:{type:String,default:void 0},inputId:{type:String,default:null},inputClass:{type:[String,Object],default:null},inputStyle:{type:Object,default:null},inputProps:{type:null,default:null},panelClass:{type:[String,Object],default:null},panelStyle:{type:Object,default:null},panelProps:{type:null,default:null},overlayClass:{type:[String,Object],default:null},overlayStyle:{type:Object,default:null},overlayProps:{type:null,default:null},appendTo:{type:[String,Object],default:"body"},loading:{type:Boolean,default:!1},dropdownIcon:{type:String,default:void 0},loadingIcon:{type:String,default:void 0},optionGroupIcon:{type:String,default:void 0},autoOptionFocus:{type:Boolean,default:!1},selectOnFocus:{type:Boolean,default:!1},focusOnHover:{type:Boolean,default:!0},searchLocale:{type:String,default:void 0},searchMessage:{type:String,default:null},selectionMessage:{type:String,default:null},emptySelectionMessage:{type:String,default:null},emptySearchMessage:{type:String,default:null},emptyMessage:{type:String,default:null},tabindex:{type:Number,default:0},ariaLabelledby:{type:String,default:null},ariaLabel:{type:String,default:null}},style:be,provide:function(){return{$pcCascadeSelect:this,$parentInstance:this}}},H={name:"CascadeSelectSub",hostName:"CascadeSelect",extends:pe,emits:["option-change","option-focus-change","option-focus-enter-change"],container:null,props:{selectId:String,focusedOptionId:String,options:Array,optionLabel:String|Function,optionValue:String,optionDisabled:null,optionGroupIcon:String,optionGroupLabel:String,optionGroupChildren:{type:[String,Array],default:null},activeOptionPath:Array,level:Number,templates:null,value:null},methods:{getOptionId:function(e){return"".concat(this.selectId,"_").concat(e.key)},getOptionLabel:function(e){return this.optionLabel?y(e.option,this.optionLabel):e.option},getOptionValue:function(e){return this.optionValue?y(e.option,this.optionValue):e.option},getPTOptions:function(e,i,n){return this.ptm(n,{context:{option:e,index:i,level:this.level,optionGroup:this.isOptionGroup(e),active:this.isOptionActive(e),focused:this.isOptionFocused(e),disabled:this.isOptionDisabled(e)}})},isOptionDisabled:function(e){return this.optionDisabled?y(e.option,this.optionDisabled):!1},getOptionGroupLabel:function(e){return this.optionGroupLabel?y(e.option,this.optionGroupLabel):null},getOptionGroupChildren:function(e){return e.children},isOptionGroup:function(e){return f(e.children)},isOptionSelected:function(e){return R(this.value,e==null?void 0:e.option)},isOptionActive:function(e){return this.activeOptionPath&&this.activeOptionPath.some(function(i){return i.key===e.key})},isOptionFocused:function(e){return this.focusedOptionId===this.getOptionId(e)},getOptionLabelToRender:function(e){return this.isOptionGroup(e)?this.getOptionGroupLabel(e):this.getOptionLabel(e)},onOptionClick:function(e,i){this.$emit("option-change",{originalEvent:e,processedOption:i,isFocus:!0})},onOptionMouseEnter:function(e,i){this.$emit("option-focus-enter-change",{originalEvent:e,processedOption:i})},onOptionMouseMove:function(e,i){this.$emit("option-focus-change",{originalEvent:e,processedOption:i})},containerRef:function(e){this.container=e},listAriaLabel:function(){return this.$primevue.config.locale.aria?this.$primevue.config.locale.aria.listLabel:void 0}},directives:{ripple:he},components:{AngleRightIcon:B}},Oe=["id","aria-label","aria-selected","aria-expanded","aria-level","aria-setsize","aria-posinset","data-p-option-group","data-p-active","data-p-focus","data-p-disabled"],me=["onClick","onMouseenter","onMousemove"];function Ie(t,e,i,n,s,o){var r=L("AngleRightIcon"),u=L("CascadeSelectSub",!0),h=_("ripple");return d(),v("ul",l({ref:o.containerRef,class:t.cx("list")},i.level===0?t.ptm("list"):t.ptm("optionList")),[(d(!0),v(ee,null,te(i.options,function(a,c){return d(),v("li",l({key:o.getOptionLabelToRender(a),id:o.getOptionId(a),class:t.cx("option",{processedOption:a}),role:"treeitem","aria-label":o.getOptionLabelToRender(a),"aria-selected":o.isOptionGroup(a)?void 0:o.isOptionSelected(a),"aria-expanded":o.isOptionGroup(a)?o.isOptionActive(a):void 0,"aria-level":i.level+1,"aria-setsize":i.options.length,"aria-posinset":c+1},{ref_for:!0},o.getPTOptions(a,c,"option"),{"data-p-option-group":o.isOptionGroup(a),"data-p-active":o.isOptionActive(a),"data-p-focus":o.isOptionFocused(a),"data-p-disabled":o.isOptionDisabled(a)}),[ie((d(),v("div",l({class:t.cx("optionContent"),onClick:function(b){return o.onOptionClick(b,a)},onMouseenter:function(b){return o.onOptionMouseEnter(b,a)},onMousemove:function(b){return o.onOptionMouseMove(b,a)}},{ref_for:!0},o.getPTOptions(a,c,"optionContent")),[i.templates.option?(d(),O(V(i.templates.option),{key:0,option:a.option,selected:o.isOptionGroup(a)?!1:o.isOptionSelected(a)},null,8,["option","selected"])):(d(),v("span",l({key:1,class:t.cx("optionText")},{ref_for:!0},o.getPTOptions(a,c,"optionText")),K(o.getOptionLabelToRender(a)),17)),o.isOptionGroup(a)?(d(),v("span",{key:2,class:I(t.cx("groupIconContainer"))},[i.templates.optiongroupicon?(d(),O(V(i.templates.optiongroupicon),{key:0,class:I(t.cx("groupIcon"))},null,8,["class"])):i.optionGroupIcon?(d(),v("span",l({key:1,class:[t.cx("groupIcon"),i.optionGroupIcon],"aria-hidden":"true"},{ref_for:!0},o.getPTOptions(a,c,"groupIcon")),null,16)):(d(),O(r,l({key:2,class:t.cx("groupIcon"),"aria-hidden":"true"},{ref_for:!0},o.getPTOptions(a,c,"groupIcon")),null,16,["class"]))],2)):x("",!0)],16,me)),[[h]]),o.isOptionGroup(a)&&o.isOptionActive(a)?(d(),O(u,{key:0,role:"group",class:I(t.cx("optionList")),selectId:i.selectId,focusedOptionId:i.focusedOptionId,options:o.getOptionGroupChildren(a),activeOptionPath:i.activeOptionPath,level:i.level+1,templates:i.templates,optionLabel:i.optionLabel,optionValue:i.optionValue,optionDisabled:i.optionDisabled,optionGroupIcon:i.optionGroupIcon,optionGroupLabel:i.optionGroupLabel,optionGroupChildren:i.optionGroupChildren,value:i.value,onOptionChange:e[0]||(e[0]=function(p){return t.$emit("option-change",p)}),onOptionFocusChange:e[1]||(e[1]=function(p){return t.$emit("option-focus-change",p)}),onOptionFocusEnterChange:e[2]||(e[2]=function(p){return t.$emit("option-focus-enter-change",p)}),pt:t.pt,unstyled:t.unstyled},null,8,["class","selectId","focusedOptionId","options","activeOptionPath","level","templates","optionLabel","optionValue","optionDisabled","optionGroupIcon","optionGroupLabel","optionGroupChildren","value","pt","unstyled"])):x("",!0)],16,Oe)}),128))],16)}H.render=Ie;function S(t){"@babel/helpers - typeof";return S=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},S(t)}function z(t,e){var i=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter(function(s){return Object.getOwnPropertyDescriptor(t,s).enumerable})),i.push.apply(i,n)}return i}function M(t){for(var e=1;e<arguments.length;e++){var i=arguments[e]!=null?arguments[e]:{};e%2?z(Object(i),!0).forEach(function(n){we(t,n,i[n])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(i)):z(Object(i)).forEach(function(n){Object.defineProperty(t,n,Object.getOwnPropertyDescriptor(i,n))})}return t}function we(t,e,i){return(e=Le(e))in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}function Le(t){var e=Se(t,"string");return S(e)=="symbol"?e:e+""}function Se(t,e){if(S(t)!="object"||!t)return t;var i=t[Symbol.toPrimitive];if(i!==void 0){var n=i.call(t,e);if(S(n)!="object")return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}var ke={name:"CascadeSelect",extends:ge,inheritAttrs:!1,emits:["change","focus","blur","click","group-change","before-show","before-hide","hide","show"],outsideClickListener:null,matchMediaListener:null,scrollHandler:null,resizeListener:null,overlay:null,searchTimeout:null,searchValue:null,data:function(){return{clicked:!1,focused:!1,focusedOptionInfo:{index:-1,level:0,parentKey:""},activeOptionPath:[],overlayVisible:!1,dirty:!1,mobileActive:!1,query:null,queryMatches:!1}},watch:{options:function(){this.autoUpdateModel()}},mounted:function(){this.autoUpdateModel(),this.bindMatchMediaListener()},beforeUnmount:function(){this.unbindOutsideClickListener(),this.unbindResizeListener(),this.unbindMatchMediaListener(),this.scrollHandler&&(this.scrollHandler.destroy(),this.scrollHandler=null),this.overlay&&(D.clear(this.overlay),this.overlay=null),this.mobileActive&&(this.mobileActive=!1)},methods:{getOptionLabel:function(e){return this.optionLabel?y(e,this.optionLabel):e},getOptionValue:function(e){return this.optionValue?y(e,this.optionValue):e},isOptionDisabled:function(e){return this.optionDisabled?y(e,this.optionDisabled):!1},getOptionGroupLabel:function(e){return this.optionGroupLabel?y(e,this.optionGroupLabel):null},getOptionGroupChildren:function(e,i){return Y(this.optionGroupChildren)?y(e,this.optionGroupChildren):y(e,this.optionGroupChildren[i])},isOptionGroup:function(e,i){return Object.prototype.hasOwnProperty.call(e,this.optionGroupChildren[i])},getProccessedOptionLabel:function(){var e=arguments.length>0&&arguments[0]!==void 0?arguments[0]:{},i=this.isProccessedOptionGroup(e);return i?this.getOptionGroupLabel(e.option,e.level):this.getOptionLabel(e.option)},isProccessedOptionGroup:function(e){return f(e==null?void 0:e.children)},show:function(e){if(this.$emit("before-show"),this.overlayVisible=!0,this.activeOptionPath=this.$filled?this.findOptionPathByValue(this.d_value):this.activeOptionPath,this.$filled&&f(this.activeOptionPath)){var i=this.activeOptionPath[this.activeOptionPath.length-1];this.focusedOptionInfo={index:i.index,level:i.level,parentKey:i.parentKey}}else this.focusedOptionInfo={index:this.autoOptionFocus?this.findFirstFocusedOptionIndex():this.findSelectedOptionIndex(),level:0,parentKey:""};e&&P(this.$refs.focusInput)},hide:function(e){var i=this,n=function(){i.$emit("before-hide"),i.overlayVisible=!1,i.clicked=!1,i.activeOptionPath=[],i.focusedOptionInfo={index:-1,level:0,parentKey:""},e&&P(i.$refs.focusInput)};setTimeout(function(){n()},0)},onFocus:function(e){this.disabled||(this.focused=!0,this.$emit("focus",e))},onBlur:function(e){var i,n;this.focused=!1,this.focusedOptionInfo={index:-1,level:0,parentKey:""},this.searchValue="",this.$emit("blur",e),(i=(n=this.formField).onBlur)===null||i===void 0||i.call(n)},onKeyDown:function(e){if(this.disabled||this.loading){e.preventDefault();return}var i=e.metaKey||e.ctrlKey;switch(e.code){case"ArrowDown":this.onArrowDownKey(e);break;case"ArrowUp":this.onArrowUpKey(e);break;case"ArrowLeft":this.onArrowLeftKey(e);break;case"ArrowRight":this.onArrowRightKey(e);break;case"Home":this.onHomeKey(e);break;case"End":this.onEndKey(e);break;case"Space":this.onSpaceKey(e);break;case"Enter":case"NumpadEnter":this.onEnterKey(e);break;case"Escape":this.onEscapeKey(e);break;case"Tab":this.onTabKey(e);break;case"PageDown":case"PageUp":case"Backspace":case"ShiftLeft":case"ShiftRight":break;default:!i&&X(e.key)&&(!this.overlayVisible&&this.show(),this.searchOptions(e,e.key));break}this.clicked=!1},onOptionChange:function(e){var i=e.processedOption,n=e.type;if(!G(i)){var s=i.index,o=i.key,r=i.level,u=i.parentKey,h=i.children,a=f(h),c=this.activeOptionPath?this.activeOptionPath.filter(function(p){return p.parentKey!==u&&p.parentKey!==o}):[];this.focusedOptionInfo={index:s,level:r,parentKey:u},!(n=="hover"&&this.queryMatches)&&(a&&c.push(i),this.activeOptionPath=c)}},onOptionClick:function(e){var i=e.originalEvent,n=e.processedOption,s=e.isFocus,o=e.isHide,r=e.preventSelection,u=n.index,h=n.key,a=n.level,c=n.parentKey,p=this.isProccessedOptionGroup(n),b=this.isSelected(n);if(b)this.activeOptionPath=this.activeOptionPath.filter(function(C){return h!==C.key&&h.startsWith(C.key)}),this.focusedOptionInfo={index:u,level:a,parentKey:c};else if(p)this.onOptionChange(e),this.onOptionGroupSelect(i,n);else{var T=this.activeOptionPath.filter(function(C){return C.parentKey!==c});T.push(n),this.focusedOptionInfo={index:u,level:a,parentKey:c},(!r||(n==null?void 0:n.children.length)!==0)&&(this.activeOptionPath=T,this.onOptionSelect(i,n,o))}s&&P(this.$refs.focusInput)},onOptionMouseEnter:function(e){this.focusOnHover&&(e.processedOption.level===0&&(this.dirty=!0),this.dirty||!this.dirty&&f(this.d_value)?this.onOptionChange(M(M({},e),{},{type:"hover"})):!this.dirty&&e.processedOption.level===0&&this.onOptionClick(M(M({},e),{},{type:"hover"})))},onOptionMouseMove:function(e){this.focused&&this.focusOnHover&&this.changeFocusedOptionIndex(e,e.processedOption.index)},onOptionSelect:function(e,i){var n=arguments.length>2&&arguments[2]!==void 0?arguments[2]:!0,s=this.getOptionValue(i==null?void 0:i.option);this.activeOptionPath.forEach(function(o){return o.selected=!0}),this.updateModel(e,s),n&&this.hide(!0)},onOptionGroupSelect:function(e,i){this.dirty=!0,this.$emit("group-change",{originalEvent:e,value:i.option})},onContainerClick:function(e){this.disabled||this.loading||e.target.getAttribute("data-pc-section")==="clearicon"||e.target.closest('[data-pc-section="clearicon"]')||((!this.overlay||!this.overlay.contains(e.target))&&(this.overlayVisible?this.hide():this.show(),P(this.$refs.focusInput)),this.clicked=!0,this.$emit("click",e))},onClearClick:function(e){this.updateModel(e,null)},onOverlayClick:function(e){ce.emit("overlay-click",{originalEvent:e,target:this.$el})},onOverlayKeyDown:function(e){switch(e.code){case"Escape":this.onEscapeKey(e);break}},onArrowDownKey:function(e){if(!this.overlayVisible)this.show();else{var i=this.focusedOptionInfo.index!==-1?this.findNextOptionIndex(this.focusedOptionInfo.index):this.clicked?this.findFirstOptionIndex():this.findFirstFocusedOptionIndex();this.changeFocusedOptionIndex(e,i,!0)}e.preventDefault()},onArrowUpKey:function(e){if(e.altKey){if(this.focusedOptionInfo.index!==-1){var i=this.visibleOptions[this.focusedOptionInfo.index],n=this.isProccessedOptionGroup(i);!n&&this.onOptionChange({originalEvent:e,processedOption:i})}this.overlayVisible&&this.hide(),e.preventDefault()}else{var s=this.focusedOptionInfo.index!==-1?this.findPrevOptionIndex(this.focusedOptionInfo.index):this.clicked?this.findLastOptionIndex():this.findLastFocusedOptionIndex();this.changeFocusedOptionIndex(e,s,!0),!this.overlayVisible&&this.show(),e.preventDefault()}},onArrowLeftKey:function(e){var i=this;if(this.overlayVisible){var n=this.visibleOptions[this.focusedOptionInfo.index],s=this.activeOptionPath.find(function(u){return u.key===(n==null?void 0:n.parentKey)}),o=this.focusedOptionInfo.parentKey===""||s&&s.key===this.focusedOptionInfo.parentKey,r=G(n==null?void 0:n.parent);o&&(this.activeOptionPath=this.activeOptionPath.filter(function(u){return u.parentKey!==i.focusedOptionInfo.parentKey})),r||(this.focusedOptionInfo={index:-1,parentKey:s?s.parentKey:""},this.searchValue="",this.onArrowDownKey(e)),e.preventDefault()}},onArrowRightKey:function(e){if(this.overlayVisible){var i=this.visibleOptions[this.focusedOptionInfo.index],n=this.isProccessedOptionGroup(i);if(n){var s=this.activeOptionPath.some(function(o){return(i==null?void 0:i.key)===o.key});s?(this.focusedOptionInfo={index:-1,parentKey:i==null?void 0:i.key},this.searchValue="",this.onArrowDownKey(e)):this.onOptionChange({originalEvent:e,processedOption:i})}e.preventDefault()}},onHomeKey:function(e){this.changeFocusedOptionIndex(e,this.findFirstOptionIndex()),!this.overlayVisible&&this.show(),e.preventDefault()},onEndKey:function(e){this.changeFocusedOptionIndex(e,this.findLastOptionIndex()),!this.overlayVisible&&this.show(),e.preventDefault()},onEnterKey:function(e){if(!this.overlayVisible)this.focusedOptionInfo.index,this.onArrowDownKey(e);else if(this.focusedOptionInfo.index!==-1){var i=this.visibleOptions[this.focusedOptionInfo.index],n=this.isProccessedOptionGroup(i);this.onOptionClick({originalEvent:e,processedOption:i,preventSelection:!1}),!n&&this.hide()}e.preventDefault()},onSpaceKey:function(e){this.onEnterKey(e)},onEscapeKey:function(e){this.overlayVisible&&this.hide(!0),e.preventDefault()},onTabKey:function(e){if(this.focusedOptionInfo.index!==-1){var i=this.visibleOptions[this.focusedOptionInfo.index],n=this.isProccessedOptionGroup(i);!n&&this.onOptionChange({originalEvent:e,processedOption:i})}this.overlayVisible&&this.hide()},onOverlayEnter:function(e){D.set("overlay",e,this.$primevue.config.zIndex.overlay),Q(e,{position:"absolute",top:"0"}),this.alignOverlay(),this.scrollInView(),this.$attrSelector&&e.setAttribute(this.$attrSelector,"")},onOverlayAfterEnter:function(){this.bindOutsideClickListener(),this.bindScrollListener(),this.bindResizeListener(),this.$emit("show")},onOverlayLeave:function(){this.unbindOutsideClickListener(),this.unbindScrollListener(),this.unbindResizeListener(),this.$emit("hide"),this.overlay=null,this.dirty=!1},onOverlayAfterLeave:function(e){D.clear(e)},alignOverlay:function(){this.appendTo==="self"?$(this.overlay,this.$el):(this.overlay.style.minWidth=Z(this.$el)+"px",J(this.overlay,this.$el))},bindOutsideClickListener:function(){var e=this;this.outsideClickListener||(this.outsideClickListener=function(i){e.overlayVisible&&e.overlay&&!e.$el.contains(i.target)&&!e.overlay.contains(i.target)&&e.hide()},document.addEventListener("click",this.outsideClickListener,!0))},unbindOutsideClickListener:function(){this.outsideClickListener&&(document.removeEventListener("click",this.outsideClickListener,!0),this.outsideClickListener=null)},bindScrollListener:function(){var e=this;this.scrollHandler||(this.scrollHandler=new se(this.$refs.container,function(){e.overlayVisible&&e.hide()})),this.scrollHandler.bindScrollListener()},unbindScrollListener:function(){this.scrollHandler&&this.scrollHandler.unbindScrollListener()},bindResizeListener:function(){var e=this;this.resizeListener||(this.resizeListener=function(){e.overlayVisible&&!W()&&e.hide()},window.addEventListener("resize",this.resizeListener))},unbindResizeListener:function(){this.resizeListener&&(window.removeEventListener("resize",this.resizeListener),this.resizeListener=null)},bindMatchMediaListener:function(){var e=this;if(!this.matchMediaListener){var i=matchMedia("(max-width: ".concat(this.breakpoint,")"));this.query=i,this.queryMatches=i.matches,this.matchMediaListener=function(){e.queryMatches=i.matches,e.mobileActive=!1},this.query.addEventListener("change",this.matchMediaListener)}},unbindMatchMediaListener:function(){this.matchMediaListener&&(this.query.removeEventListener("change",this.matchMediaListener),this.matchMediaListener=null)},isOptionMatched:function(e){var i;return this.isValidOption(e)&&((i=this.getProccessedOptionLabel(e))===null||i===void 0?void 0:i.toLocaleLowerCase(this.searchLocale).startsWith(this.searchValue.toLocaleLowerCase(this.searchLocale)))},isValidOption:function(e){return f(e)&&!this.isOptionDisabled(e.option)},isValidSelectedOption:function(e){return this.isValidOption(e)&&this.isSelected(e)},isSelected:function(e){return this.activeOptionPath&&this.activeOptionPath.some(function(i){return i.key===e.key})},findFirstOptionIndex:function(){var e=this;return this.visibleOptions.findIndex(function(i){return e.isValidOption(i)})},findLastOptionIndex:function(){var e=this;return F(this.visibleOptions,function(i){return e.isValidOption(i)})},findNextOptionIndex:function(e){var i=this,n=e<this.visibleOptions.length-1?this.visibleOptions.slice(e+1).findIndex(function(s){return i.isValidOption(s)}):-1;return n>-1?n+e+1:e},findPrevOptionIndex:function(e){var i=this,n=e>0?F(this.visibleOptions.slice(0,e),function(s){return i.isValidOption(s)}):-1;return n>-1?n:e},findSelectedOptionIndex:function(){var e=this;return this.visibleOptions.findIndex(function(i){return e.isValidSelectedOption(i)})},findFirstFocusedOptionIndex:function(){var e=this.findSelectedOptionIndex();return e<0?this.findFirstOptionIndex():e},findLastFocusedOptionIndex:function(){var e=this.findSelectedOptionIndex();return e<0?this.findLastOptionIndex():e},findOptionPathByValue:function(e,i){var n=arguments.length>2&&arguments[2]!==void 0?arguments[2]:0;if(i=i||n===0&&this.processedOptions,!i)return null;if(G(e))return[];for(var s=0;s<i.length;s++){var o=i[s];if(R(e,this.getOptionValue(o.option),this.equalityKey))return[o];var r=this.findOptionPathByValue(e,o.children,n+1);if(r)return r.unshift(o),r}},searchOptions:function(e,i){var n=this;this.searchValue=(this.searchValue||"")+i;var s=-1,o=!1;return f(this.searchValue)&&(this.focusedOptionInfo.index!==-1?(s=this.visibleOptions.slice(this.focusedOptionInfo.index).findIndex(function(r){return n.isOptionMatched(r)}),s=s===-1?this.visibleOptions.slice(0,this.focusedOptionInfo.index).findIndex(function(r){return n.isOptionMatched(r)}):s+this.focusedOptionInfo.index):s=this.visibleOptions.findIndex(function(r){return n.isOptionMatched(r)}),s!==-1&&(o=!0),s===-1&&this.focusedOptionInfo.index===-1&&(s=this.findFirstFocusedOptionIndex()),s!==-1&&this.changeFocusedOptionIndex(e,s)),this.searchTimeout&&clearTimeout(this.searchTimeout),this.searchTimeout=setTimeout(function(){n.searchValue="",n.searchTimeout=null},500),o},changeFocusedOptionIndex:function(e,i,n){this.focusedOptionInfo.index!==i&&(this.focusedOptionInfo.index=i,this.scrollInView(),this.focusOnHover&&this.onOptionClick({originalEvent:e,processedOption:this.visibleOptions[i],isHide:!1,preventSelection:n}),this.selectOnFocus&&this.onOptionChange({originalEvent:e,processedOption:this.visibleOptions[i],isHide:!1}))},scrollInView:function(){var e=this,i=arguments.length>0&&arguments[0]!==void 0?arguments[0]:-1;this.$nextTick(function(){var n=i!==-1?"".concat(e.$id,"_").concat(i):e.focusedOptionId,s=U(e.list,'li[id="'.concat(n,'"]'));s&&s.scrollIntoView&&s.scrollIntoView({block:"nearest",inline:"start"})})},autoUpdateModel:function(){this.selectOnFocus&&this.autoOptionFocus&&!this.$filled&&(this.focusedOptionInfo.index=this.findFirstFocusedOptionIndex(),this.onOptionChange({processedOption:this.visibleOptions[this.focusedOptionInfo.index],isHide:!1}),!this.overlayVisible&&(this.focusedOptionInfo={index:-1,level:0,parentKey:""}))},updateModel:function(e,i){this.writeValue(i,e),this.$emit("change",{originalEvent:e,value:i})},createProcessedOptions:function(e){var i=this,n=arguments.length>1&&arguments[1]!==void 0?arguments[1]:0,s=arguments.length>2&&arguments[2]!==void 0?arguments[2]:{},o=arguments.length>3&&arguments[3]!==void 0?arguments[3]:"",r=[];return e&&e.forEach(function(u,h){var a=(o!==""?o+"_":"")+h,c={option:u,index:h,level:n,key:a,parent:s,parentKey:o};c.children=i.createProcessedOptions(i.getOptionGroupChildren(u,n),n+1,c,a),r.push(c)}),r},overlayRef:function(e){this.overlay=e}},computed:{hasSelectedOption:function(){return this.$filled},label:function(){var e=this.placeholder||"p-emptylabel";if(this.$filled){var i=this.findOptionPathByValue(this.d_value),n=f(i)?i[i.length-1]:null;return n?this.getOptionLabel(n.option):e}return e},processedOptions:function(){return this.createProcessedOptions(this.options||[])},visibleOptions:function(){var e=this,i=this.activeOptionPath&&this.activeOptionPath.find(function(n){return n.key===e.focusedOptionInfo.parentKey});return i?i.children:this.processedOptions},equalityKey:function(){return this.optionValue?null:this.dataKey},searchResultMessageText:function(){return f(this.visibleOptions)?this.searchMessageText.replaceAll("{0}",this.visibleOptions.length):this.emptySearchMessageText},searchMessageText:function(){return this.searchMessage||this.$primevue.config.locale.searchMessage||""},emptySearchMessageText:function(){return this.emptySearchMessage||this.$primevue.config.locale.emptySearchMessage||""},emptyMessageText:function(){return this.emptyMessage||this.$primevue.config.locale.emptyMessage||""},selectionMessageText:function(){return this.selectionMessage||this.$primevue.config.locale.selectionMessage||""},emptySelectionMessageText:function(){return this.emptySelectionMessage||this.$primevue.config.locale.emptySelectionMessage||""},selectedMessageText:function(){return this.$filled?this.selectionMessageText.replaceAll("{0}","1"):this.emptySelectionMessageText},focusedOptionId:function(){return this.focusedOptionInfo.index!==-1?"".concat(this.$id).concat(f(this.focusedOptionInfo.parentKey)?"_"+this.focusedOptionInfo.parentKey:"","_").concat(this.focusedOptionInfo.index):null},isClearIconVisible:function(){return this.showClear&&this.d_value!=null&&f(this.options)}},components:{CascadeSelectSub:H,Portal:de,ChevronDownIcon:ae,SpinnerIcon:re,AngleRightIcon:B,TimesIcon:le}};function k(t){"@babel/helpers - typeof";return k=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},k(t)}function j(t,e){var i=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter(function(s){return Object.getOwnPropertyDescriptor(t,s).enumerable})),i.push.apply(i,n)}return i}function w(t){for(var e=1;e<arguments.length;e++){var i=arguments[e]!=null?arguments[e]:{};e%2?j(Object(i),!0).forEach(function(n){Ce(t,n,i[n])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(i)):j(Object(i)).forEach(function(n){Object.defineProperty(t,n,Object.getOwnPropertyDescriptor(i,n))})}return t}function Ce(t,e,i){return(e=Pe(e))in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}function Pe(t){var e=Me(t,"string");return k(e)=="symbol"?e:e+""}function Me(t,e){if(k(t)!="object"||!t)return t;var i=t[Symbol.toPrimitive];if(i!==void 0){var n=i.call(t,e);if(k(n)!="object")return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}var Ke=["id","disabled","placeholder","tabindex","aria-label","aria-labelledby","aria-expanded","aria-controls","aria-activedescendant","aria-invalid"];function xe(t,e,i,n,s,o){var r=L("SpinnerIcon"),u=L("CascadeSelectSub"),h=L("Portal");return d(),v("div",l({ref:"container",class:t.cx("root"),style:t.sx("root"),onClick:e[5]||(e[5]=function(a){return o.onContainerClick(a)})},t.ptmi("root")),[g("div",l({class:"p-hidden-accessible"},t.ptm("hiddenInputContainer"),{"data-p-hidden-accessible":!0}),[g("input",l({ref:"focusInput",id:t.inputId,type:"text",class:t.inputClass,style:t.inputStyle,readonly:"",disabled:t.disabled,placeholder:t.placeholder,tabindex:t.disabled?-1:t.tabindex,role:"combobox","aria-label":t.ariaLabel,"aria-labelledby":t.ariaLabelledby,"aria-haspopup":"tree","aria-expanded":s.overlayVisible,"aria-controls":t.$id+"_tree","aria-activedescendant":s.focused?o.focusedOptionId:void 0,"aria-invalid":t.invalid||void 0,onFocus:e[0]||(e[0]=function(){return o.onFocus&&o.onFocus.apply(o,arguments)}),onBlur:e[1]||(e[1]=function(){return o.onBlur&&o.onBlur.apply(o,arguments)}),onKeydown:e[2]||(e[2]=function(){return o.onKeyDown&&o.onKeyDown.apply(o,arguments)})},w(w({},t.inputProps),t.ptm("hiddenInput"))),null,16,Ke)],16),g("span",l({class:t.cx("label")},t.ptm("label")),[m(t.$slots,"value",{value:t.d_value,placeholder:t.placeholder},function(){return[ne(K(o.label),1)]})],16),o.isClearIconVisible?m(t.$slots,"clearicon",{key:0,class:I(t.cx("clearIcon")),clearCallback:o.onClearClick},function(){return[(d(),O(V(t.clearIcon?"i":"TimesIcon"),l({ref:"clearIcon",class:[t.cx("clearIcon"),t.clearIcon],onClick:o.onClearClick},t.ptm("clearIcon"),{"data-pc-section":"clearicon"}),null,16,["class","onClick"]))]}):x("",!0),g("div",l({class:t.cx("dropdown"),role:"button",tabindex:"-1"},t.ptm("dropdown")),[t.loading?m(t.$slots,"loadingicon",{key:0,class:I(t.cx("loadingIcon"))},function(){return[t.loadingIcon?(d(),v("span",l({key:0,class:[t.cx("loadingIcon"),"pi-spin",t.loadingIcon],"aria-hidden":"true"},t.ptm("loadingIcon")),null,16)):(d(),O(r,l({key:1,class:t.cx("loadingIcon"),spin:"","aria-hidden":"true"},t.ptm("loadingIcon")),null,16,["class"]))]}):m(t.$slots,"dropdownicon",{key:1,class:I(t.cx("dropdownIcon"))},function(){return[(d(),O(V(t.dropdownIcon?"span":"ChevronDownIcon"),l({class:[t.cx("dropdownIcon"),t.dropdownIcon],"aria-hidden":"true"},t.ptm("dropdownIcon")),null,16,["class"]))]})],16),g("span",l({role:"status","aria-live":"polite",class:"p-hidden-accessible"},t.ptm("hiddenSearchResult"),{"data-p-hidden-accessible":!0}),K(o.searchResultMessageText),17),E(h,{appendTo:t.appendTo},{default:A(function(){return[E(oe,l({name:"p-connected-overlay",onEnter:o.onOverlayEnter,onAfterEnter:o.onOverlayAfterEnter,onLeave:o.onOverlayLeave,onAfterLeave:o.onOverlayAfterLeave},t.ptm("transition")),{default:A(function(){return[s.overlayVisible?(d(),v("div",l({key:0,ref:o.overlayRef,class:[t.cx("overlay"),t.panelClass,t.overlayClass],style:[t.panelStyle,t.overlayStyle],onClick:e[3]||(e[3]=function(){return o.onOverlayClick&&o.onOverlayClick.apply(o,arguments)}),onKeydown:e[4]||(e[4]=function(){return o.onOverlayKeyDown&&o.onOverlayKeyDown.apply(o,arguments)})},w(w(w({},t.panelProps),t.overlayProps),t.ptm("overlay"))),[m(t.$slots,"header",{value:t.d_value,options:t.options}),g("div",l({class:t.cx("listContainer")},t.ptm("listContainer")),[E(u,{id:t.$id+"_tree",role:"tree","aria-orientation":"horizontal",selectId:t.$id,focusedOptionId:s.focused?o.focusedOptionId:void 0,options:o.processedOptions,activeOptionPath:s.activeOptionPath,level:0,templates:t.$slots,optionLabel:t.optionLabel,optionValue:t.optionValue,optionDisabled:t.optionDisabled,optionGroupIcon:t.optionGroupIcon,optionGroupLabel:t.optionGroupLabel,optionGroupChildren:t.optionGroupChildren,value:t.d_value,onOptionChange:o.onOptionClick,onOptionFocusChange:o.onOptionMouseMove,onOptionFocusEnterChange:o.onOptionMouseEnter,pt:t.pt,unstyled:t.unstyled},null,8,["id","selectId","focusedOptionId","options","activeOptionPath","templates","optionLabel","optionValue","optionDisabled","optionGroupIcon","optionGroupLabel","optionGroupChildren","value","onOptionChange","onOptionFocusChange","onOptionFocusEnterChange","pt","unstyled"])],16),g("span",l({role:"status","aria-live":"polite",class:"p-hidden-accessible"},t.ptm("hiddenSelectedMessage"),{"data-p-hidden-accessible":!0}),K(o.selectedMessageText),17),m(t.$slots,"footer",{value:t.d_value,options:t.options})],16)):x("",!0)]}),_:3},16,["onEnter","onAfterEnter","onLeave","onAfterLeave"])]}),_:3},8,["appendTo"])],16)}ke.render=xe;export{ke as default};
