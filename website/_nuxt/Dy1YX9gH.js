import{c as H}from"./Be1fzYNM.js";import{v as le,B as se,x as C,J as oe,a7 as re,z as ae,y as B,C as G,$ as de,D as ue,E as ce,G as pe,H as he,I as fe,A as x,a8 as me,a9 as be,U as ge,K as T,L as I,M as ye,c as u,o as r,a as y,N as m,l as g,b as V,P as o,F as M,d as A,t as v,s as q,w as S,O as k,q as O,Q as D,T as ve,aa as Oe,R as Ie,S as Se}from"./DWUyzV_3.js";import{Z as j}from"./DGEOgZpe.js";import{C as ke}from"./By77zot4.js";import{s as we}from"./1XHi02cs.js";import{s as Le}from"./BftmRrQc.js";import{s as Fe}from"./kftMLlWQ.js";import{s as Ce}from"./By04JIrE.js";import{s as xe}from"./C50oHOL0.js";import Ve from"./epWB24ap.js";import Ke from"./D2pJApHx.js";import Te from"./BrVI1-wI.js";import Me from"./Dsksf0sy.js";import De from"./lezfXGT4.js";import{O as Ae}from"./D5K4UopI.js";import{s as Ee}from"./t0wgrPTa.js";import{R as Pe}from"./B3MqGaNe.js";import Re from"./DgSaIOFV.js";import{s as ze}from"./Djaqmldt.js";import"./A4PA4AXe.js";import"./B-kZlFfU.js";import"./BBPg_8E7.js";import"./x_TrJ5H1.js";import"./Ds2a9wz8.js";import"./DmfvTtO6.js";import"./C_hGxOta.js";var He=le`
    .p-multiselect {
        display: inline-flex;
        cursor: pointer;
        position: relative;
        user-select: none;
        background: dt('multiselect.background');
        border: 1px solid dt('multiselect.border.color');
        transition:
            background dt('multiselect.transition.duration'),
            color dt('multiselect.transition.duration'),
            border-color dt('multiselect.transition.duration'),
            outline-color dt('multiselect.transition.duration'),
            box-shadow dt('multiselect.transition.duration');
        border-radius: dt('multiselect.border.radius');
        outline-color: transparent;
        box-shadow: dt('multiselect.shadow');
    }

    .p-multiselect:not(.p-disabled):hover {
        border-color: dt('multiselect.hover.border.color');
    }

    .p-multiselect:not(.p-disabled).p-focus {
        border-color: dt('multiselect.focus.border.color');
        box-shadow: dt('multiselect.focus.ring.shadow');
        outline: dt('multiselect.focus.ring.width') dt('multiselect.focus.ring.style') dt('multiselect.focus.ring.color');
        outline-offset: dt('multiselect.focus.ring.offset');
    }

    .p-multiselect.p-variant-filled {
        background: dt('multiselect.filled.background');
    }

    .p-multiselect.p-variant-filled:not(.p-disabled):hover {
        background: dt('multiselect.filled.hover.background');
    }

    .p-multiselect.p-variant-filled.p-focus {
        background: dt('multiselect.filled.focus.background');
    }

    .p-multiselect.p-invalid {
        border-color: dt('multiselect.invalid.border.color');
    }

    .p-multiselect.p-disabled {
        opacity: 1;
        background: dt('multiselect.disabled.background');
    }

    .p-multiselect-dropdown {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-shrink: 0;
        background: transparent;
        color: dt('multiselect.dropdown.color');
        width: dt('multiselect.dropdown.width');
        border-start-end-radius: dt('multiselect.border.radius');
        border-end-end-radius: dt('multiselect.border.radius');
    }

    .p-multiselect-clear-icon {
        position: absolute;
        top: 50%;
        margin-top: -0.5rem;
        color: dt('multiselect.clear.icon.color');
        inset-inline-end: dt('multiselect.dropdown.width');
    }

    .p-multiselect-label-container {
        overflow: hidden;
        flex: 1 1 auto;
        cursor: pointer;
    }

    .p-multiselect-label {
        display: flex;
        align-items: center;
        gap: calc(dt('multiselect.padding.y') / 2);
        white-space: nowrap;
        cursor: pointer;
        overflow: hidden;
        text-overflow: ellipsis;
        padding: dt('multiselect.padding.y') dt('multiselect.padding.x');
        color: dt('multiselect.color');
    }

    .p-multiselect-label.p-placeholder {
        color: dt('multiselect.placeholder.color');
    }

    .p-multiselect.p-invalid .p-multiselect-label.p-placeholder {
        color: dt('multiselect.invalid.placeholder.color');
    }

    .p-multiselect.p-disabled .p-multiselect-label {
        color: dt('multiselect.disabled.color');
    }

    .p-multiselect-label-empty {
        overflow: hidden;
        visibility: hidden;
    }

    .p-multiselect .p-multiselect-overlay {
        min-width: 100%;
    }

    .p-multiselect-overlay {
        position: absolute;
        top: 0;
        left: 0;
        background: dt('multiselect.overlay.background');
        color: dt('multiselect.overlay.color');
        border: 1px solid dt('multiselect.overlay.border.color');
        border-radius: dt('multiselect.overlay.border.radius');
        box-shadow: dt('multiselect.overlay.shadow');
    }

    .p-multiselect-header {
        display: flex;
        align-items: center;
        padding: dt('multiselect.list.header.padding');
    }

    .p-multiselect-header .p-checkbox {
        margin-inline-end: dt('multiselect.option.gap');
    }

    .p-multiselect-filter-container {
        flex: 1 1 auto;
    }

    .p-multiselect-filter {
        width: 100%;
    }

    .p-multiselect-list-container {
        overflow: auto;
    }

    .p-multiselect-list {
        margin: 0;
        padding: 0;
        list-style-type: none;
        padding: dt('multiselect.list.padding');
        display: flex;
        flex-direction: column;
        gap: dt('multiselect.list.gap');
    }

    .p-multiselect-option {
        cursor: pointer;
        font-weight: normal;
        white-space: nowrap;
        position: relative;
        overflow: hidden;
        display: flex;
        align-items: center;
        gap: dt('multiselect.option.gap');
        padding: dt('multiselect.option.padding');
        border: 0 none;
        color: dt('multiselect.option.color');
        background: transparent;
        transition:
            background dt('multiselect.transition.duration'),
            color dt('multiselect.transition.duration'),
            border-color dt('multiselect.transition.duration'),
            box-shadow dt('multiselect.transition.duration'),
            outline-color dt('multiselect.transition.duration');
        border-radius: dt('multiselect.option.border.radius');
    }

    .p-multiselect-option:not(.p-multiselect-option-selected):not(.p-disabled).p-focus {
        background: dt('multiselect.option.focus.background');
        color: dt('multiselect.option.focus.color');
    }

    .p-multiselect-option.p-multiselect-option-selected {
        background: dt('multiselect.option.selected.background');
        color: dt('multiselect.option.selected.color');
    }

    .p-multiselect-option.p-multiselect-option-selected.p-focus {
        background: dt('multiselect.option.selected.focus.background');
        color: dt('multiselect.option.selected.focus.color');
    }

    .p-multiselect-option-group {
        cursor: auto;
        margin: 0;
        padding: dt('multiselect.option.group.padding');
        background: dt('multiselect.option.group.background');
        color: dt('multiselect.option.group.color');
        font-weight: dt('multiselect.option.group.font.weight');
    }

    .p-multiselect-empty-message {
        padding: dt('multiselect.empty.message.padding');
    }

    .p-multiselect-label .p-chip {
        padding-block-start: calc(dt('multiselect.padding.y') / 2);
        padding-block-end: calc(dt('multiselect.padding.y') / 2);
        border-radius: dt('multiselect.chip.border.radius');
    }

    .p-multiselect-label:has(.p-chip) {
        padding: calc(dt('multiselect.padding.y') / 2) calc(dt('multiselect.padding.x') / 2);
    }

    .p-multiselect-fluid {
        display: flex;
        width: 100%;
    }

    .p-multiselect-sm .p-multiselect-label {
        font-size: dt('multiselect.sm.font.size');
        padding-block: dt('multiselect.sm.padding.y');
        padding-inline: dt('multiselect.sm.padding.x');
    }

    .p-multiselect-sm .p-multiselect-dropdown .p-icon {
        font-size: dt('multiselect.sm.font.size');
        width: dt('multiselect.sm.font.size');
        height: dt('multiselect.sm.font.size');
    }

    .p-multiselect-lg .p-multiselect-label {
        font-size: dt('multiselect.lg.font.size');
        padding-block: dt('multiselect.lg.padding.y');
        padding-inline: dt('multiselect.lg.padding.x');
    }

    .p-multiselect-lg .p-multiselect-dropdown .p-icon {
        font-size: dt('multiselect.lg.font.size');
        width: dt('multiselect.lg.font.size');
        height: dt('multiselect.lg.font.size');
    }
`,Be={root:function(e){var i=e.props;return{position:i.appendTo==="self"?"relative":void 0}}},Ge={root:function(e){var i=e.instance,l=e.props;return["p-multiselect p-component p-inputwrapper",{"p-multiselect-display-chip":l.display==="chip","p-disabled":l.disabled,"p-invalid":i.$invalid,"p-variant-filled":i.$variant==="filled","p-focus":i.focused,"p-inputwrapper-filled":i.$filled,"p-inputwrapper-focus":i.focused||i.overlayVisible,"p-multiselect-open":i.overlayVisible,"p-multiselect-fluid":i.$fluid,"p-multiselect-sm p-inputfield-sm":l.size==="small","p-multiselect-lg p-inputfield-lg":l.size==="large"}]},labelContainer:"p-multiselect-label-container",label:function(e){var i=e.instance,l=e.props;return["p-multiselect-label",{"p-placeholder":i.label===l.placeholder,"p-multiselect-label-empty":!l.placeholder&&!i.$filled}]},clearIcon:"p-multiselect-clear-icon",chipItem:"p-multiselect-chip-item",pcChip:"p-multiselect-chip",chipIcon:"p-multiselect-chip-icon",dropdown:"p-multiselect-dropdown",loadingIcon:"p-multiselect-loading-icon",dropdownIcon:"p-multiselect-dropdown-icon",overlay:"p-multiselect-overlay p-component",header:"p-multiselect-header",pcFilterContainer:"p-multiselect-filter-container",pcFilter:"p-multiselect-filter",listContainer:"p-multiselect-list-container",list:"p-multiselect-list",optionGroup:"p-multiselect-option-group",option:function(e){var i=e.instance,l=e.option,s=e.index,n=e.getItemOptions,c=e.props;return["p-multiselect-option",{"p-multiselect-option-selected":i.isSelected(l)&&c.highlightOnSelect,"p-focus":i.focusedOptionIndex===i.getOptionIndex(s,n),"p-disabled":i.isOptionDisabled(l)}]},emptyMessage:"p-multiselect-empty-message"},Ne=se.extend({name:"multiselect",style:He,classes:Ge,inlineStyles:Be}),Ue={name:"BaseMultiSelect",extends:ze,props:{options:Array,optionLabel:null,optionValue:null,optionDisabled:null,optionGroupLabel:null,optionGroupChildren:null,scrollHeight:{type:String,default:"14rem"},placeholder:String,inputId:{type:String,default:null},panelClass:{type:String,default:null},panelStyle:{type:null,default:null},overlayClass:{type:String,default:null},overlayStyle:{type:null,default:null},dataKey:null,showClear:{type:Boolean,default:!1},clearIcon:{type:String,default:void 0},resetFilterOnClear:{type:Boolean,default:!1},filter:Boolean,filterPlaceholder:String,filterLocale:String,filterMatchMode:{type:String,default:"contains"},filterFields:{type:Array,default:null},appendTo:{type:[String,Object],default:"body"},display:{type:String,default:"comma"},selectedItemsLabel:{type:String,default:null},maxSelectedLabels:{type:Number,default:null},selectionLimit:{type:Number,default:null},showToggleAll:{type:Boolean,default:!0},loading:{type:Boolean,default:!1},checkboxIcon:{type:String,default:void 0},dropdownIcon:{type:String,default:void 0},filterIcon:{type:String,default:void 0},loadingIcon:{type:String,default:void 0},removeTokenIcon:{type:String,default:void 0},chipIcon:{type:String,default:void 0},selectAll:{type:Boolean,default:null},resetFilterOnHide:{type:Boolean,default:!1},virtualScrollerOptions:{type:Object,default:null},autoOptionFocus:{type:Boolean,default:!1},autoFilterFocus:{type:Boolean,default:!1},focusOnHover:{type:Boolean,default:!0},highlightOnSelect:{type:Boolean,default:!1},filterMessage:{type:String,default:null},selectionMessage:{type:String,default:null},emptySelectionMessage:{type:String,default:null},emptyFilterMessage:{type:String,default:null},emptyMessage:{type:String,default:null},tabindex:{type:Number,default:0},ariaLabel:{type:String,default:null},ariaLabelledby:{type:String,default:null}},style:Ne,provide:function(){return{$pcMultiSelect:this,$parentInstance:this}}};function E(t){"@babel/helpers - typeof";return E=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},E(t)}function W(t,e){var i=Object.keys(t);if(Object.getOwnPropertySymbols){var l=Object.getOwnPropertySymbols(t);e&&(l=l.filter(function(s){return Object.getOwnPropertyDescriptor(t,s).enumerable})),i.push.apply(i,l)}return i}function Z(t){for(var e=1;e<arguments.length;e++){var i=arguments[e]!=null?arguments[e]:{};e%2?W(Object(i),!0).forEach(function(l){w(t,l,i[l])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(i)):W(Object(i)).forEach(function(l){Object.defineProperty(t,l,Object.getOwnPropertyDescriptor(i,l))})}return t}function w(t,e,i){return(e=je(e))in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}function je(t){var e=$e(t,"string");return E(e)=="symbol"?e:e+""}function $e(t,e){if(E(t)!="object"||!t)return t;var i=t[Symbol.toPrimitive];if(i!==void 0){var l=i.call(t,e);if(E(l)!="object")return l;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}function J(t){return Je(t)||Ze(t)||We(t)||qe()}function qe(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function We(t,e){if(t){if(typeof t=="string")return $(t,e);var i={}.toString.call(t).slice(8,-1);return i==="Object"&&t.constructor&&(i=t.constructor.name),i==="Map"||i==="Set"?Array.from(t):i==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(i)?$(t,e):void 0}}function Ze(t){if(typeof Symbol<"u"&&t[Symbol.iterator]!=null||t["@@iterator"]!=null)return Array.from(t)}function Je(t){if(Array.isArray(t))return $(t)}function $(t,e){(e==null||e>t.length)&&(e=t.length);for(var i=0,l=Array(e);i<e;i++)l[i]=t[i];return l}var Qe={name:"MultiSelect",extends:Ue,inheritAttrs:!1,emits:["change","focus","blur","before-show","before-hide","show","hide","filter","selectall-change"],inject:{$pcFluid:{default:null}},outsideClickListener:null,scrollHandler:null,resizeListener:null,overlay:null,list:null,virtualScroller:null,startRangeIndex:-1,searchTimeout:null,searchValue:"",selectOnFocus:!1,data:function(){return{clicked:!1,focused:!1,focusedOptionIndex:-1,filterValue:null,overlayVisible:!1}},watch:{options:function(){this.autoUpdateModel()}},mounted:function(){this.autoUpdateModel()},beforeUnmount:function(){this.unbindOutsideClickListener(),this.unbindResizeListener(),this.scrollHandler&&(this.scrollHandler.destroy(),this.scrollHandler=null),this.overlay&&(j.clear(this.overlay),this.overlay=null)},methods:{getOptionIndex:function(e,i){return this.virtualScrollerDisabled?e:i&&i(e).index},getOptionLabel:function(e){return this.optionLabel?T(e,this.optionLabel):e},getOptionValue:function(e){return this.optionValue?T(e,this.optionValue):e},getOptionRenderKey:function(e,i){return this.dataKey?T(e,this.dataKey):this.getOptionLabel(e)+"_".concat(i)},getHeaderCheckboxPTOptions:function(e){return this.ptm(e,{context:{selected:this.allSelected}})},getCheckboxPTOptions:function(e,i,l,s){return this.ptm(s,{context:{selected:this.isSelected(e),focused:this.focusedOptionIndex===this.getOptionIndex(l,i),disabled:this.isOptionDisabled(e)}})},isOptionDisabled:function(e){return this.maxSelectionLimitReached&&!this.isSelected(e)?!0:this.optionDisabled?T(e,this.optionDisabled):!1},isOptionGroup:function(e){return this.optionGroupLabel&&e.optionGroup&&e.group},getOptionGroupLabel:function(e){return T(e,this.optionGroupLabel)},getOptionGroupChildren:function(e){return T(e,this.optionGroupChildren)},getAriaPosInset:function(e){var i=this;return(this.optionGroupLabel?e-this.visibleOptions.slice(0,e).filter(function(l){return i.isOptionGroup(l)}).length:e)+1},show:function(e){this.$emit("before-show"),this.overlayVisible=!0,this.focusedOptionIndex=this.focusedOptionIndex!==-1?this.focusedOptionIndex:this.autoOptionFocus?this.findFirstFocusedOptionIndex():this.findSelectedOptionIndex(),e&&x(this.$refs.focusInput)},hide:function(e){var i=this,l=function(){i.$emit("before-hide"),i.overlayVisible=!1,i.clicked=!1,i.focusedOptionIndex=-1,i.searchValue="",i.resetFilterOnHide&&(i.filterValue=null),e&&x(i.$refs.focusInput)};setTimeout(function(){l()},0)},onFocus:function(e){this.disabled||(this.focused=!0,this.overlayVisible&&(this.focusedOptionIndex=this.focusedOptionIndex!==-1?this.focusedOptionIndex:this.autoOptionFocus?this.findFirstFocusedOptionIndex():this.findSelectedOptionIndex(),!this.autoFilterFocus&&this.scrollInView(this.focusedOptionIndex)),this.$emit("focus",e))},onBlur:function(e){var i,l;this.clicked=!1,this.focused=!1,this.focusedOptionIndex=-1,this.searchValue="",this.$emit("blur",e),(i=(l=this.formField).onBlur)===null||i===void 0||i.call(l)},onKeyDown:function(e){var i=this;if(this.disabled){e.preventDefault();return}var l=e.metaKey||e.ctrlKey;switch(e.code){case"ArrowDown":this.onArrowDownKey(e);break;case"ArrowUp":this.onArrowUpKey(e);break;case"Home":this.onHomeKey(e);break;case"End":this.onEndKey(e);break;case"PageDown":this.onPageDownKey(e);break;case"PageUp":this.onPageUpKey(e);break;case"Enter":case"NumpadEnter":case"Space":this.onEnterKey(e);break;case"Escape":this.onEscapeKey(e);break;case"Tab":this.onTabKey(e);break;case"ShiftLeft":case"ShiftRight":this.onShiftKey(e);break;default:if(e.code==="KeyA"&&l){var s=this.visibleOptions.filter(function(n){return i.isValidOption(n)}).map(function(n){return i.getOptionValue(n)});this.updateModel(e,s),e.preventDefault();break}!l&&ge(e.key)&&(!this.overlayVisible&&this.show(),this.searchOptions(e),e.preventDefault());break}this.clicked=!1},onContainerClick:function(e){this.disabled||this.loading||e.target.tagName==="INPUT"||e.target.getAttribute("data-pc-section")==="clearicon"||e.target.closest('[data-pc-section="clearicon"]')||((!this.overlay||!this.overlay.contains(e.target))&&(this.overlayVisible?this.hide(!0):this.show(!0)),this.clicked=!0)},onClearClick:function(e){this.updateModel(e,null),this.resetFilterOnClear&&(this.filterValue=null)},onFirstHiddenFocus:function(e){var i=e.relatedTarget===this.$refs.focusInput?be(this.overlay,':not([data-p-hidden-focusable="true"])'):this.$refs.focusInput;x(i)},onLastHiddenFocus:function(e){var i=e.relatedTarget===this.$refs.focusInput?me(this.overlay,':not([data-p-hidden-focusable="true"])'):this.$refs.focusInput;x(i)},onOptionSelect:function(e,i){var l=this,s=arguments.length>2&&arguments[2]!==void 0?arguments[2]:-1,n=arguments.length>3&&arguments[3]!==void 0?arguments[3]:!1;if(!(this.disabled||this.isOptionDisabled(i))){var c=this.isSelected(i),h=null;c?h=this.d_value.filter(function(f){return!G(f,l.getOptionValue(i),l.equalityKey)}):h=[].concat(J(this.d_value||[]),[this.getOptionValue(i)]),this.updateModel(e,h),s!==-1&&(this.focusedOptionIndex=s),n&&x(this.$refs.focusInput)}},onOptionMouseMove:function(e,i){this.focusOnHover&&this.changeFocusedOptionIndex(e,i)},onOptionSelectRange:function(e){var i=this,l=arguments.length>1&&arguments[1]!==void 0?arguments[1]:-1,s=arguments.length>2&&arguments[2]!==void 0?arguments[2]:-1;if(l===-1&&(l=this.findNearestSelectedOptionIndex(s,!0)),s===-1&&(s=this.findNearestSelectedOptionIndex(l)),l!==-1&&s!==-1){var n=Math.min(l,s),c=Math.max(l,s),h=this.visibleOptions.slice(n,c+1).filter(function(f){return i.isValidOption(f)}).map(function(f){return i.getOptionValue(f)});this.updateModel(e,h)}},onFilterChange:function(e){var i=e.target.value;this.filterValue=i,this.focusedOptionIndex=-1,this.$emit("filter",{originalEvent:e,value:i}),!this.virtualScrollerDisabled&&this.virtualScroller.scrollToIndex(0)},onFilterKeyDown:function(e){switch(e.code){case"ArrowDown":this.onArrowDownKey(e);break;case"ArrowUp":this.onArrowUpKey(e,!0);break;case"ArrowLeft":case"ArrowRight":this.onArrowLeftKey(e,!0);break;case"Home":this.onHomeKey(e,!0);break;case"End":this.onEndKey(e,!0);break;case"Enter":case"NumpadEnter":this.onEnterKey(e);break;case"Escape":this.onEscapeKey(e);break;case"Tab":this.onTabKey(e,!0);break}},onFilterBlur:function(){this.focusedOptionIndex=-1},onFilterUpdated:function(){this.overlayVisible&&this.alignOverlay()},onOverlayClick:function(e){Ae.emit("overlay-click",{originalEvent:e,target:this.$el})},onOverlayKeyDown:function(e){switch(e.code){case"Escape":this.onEscapeKey(e);break}},onArrowDownKey:function(e){if(!this.overlayVisible)this.show();else{var i=this.focusedOptionIndex!==-1?this.findNextOptionIndex(this.focusedOptionIndex):this.clicked?this.findFirstOptionIndex():this.findFirstFocusedOptionIndex();e.shiftKey&&this.onOptionSelectRange(e,this.startRangeIndex,i),this.changeFocusedOptionIndex(e,i)}e.preventDefault()},onArrowUpKey:function(e){var i=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1;if(e.altKey&&!i)this.focusedOptionIndex!==-1&&this.onOptionSelect(e,this.visibleOptions[this.focusedOptionIndex]),this.overlayVisible&&this.hide(),e.preventDefault();else{var l=this.focusedOptionIndex!==-1?this.findPrevOptionIndex(this.focusedOptionIndex):this.clicked?this.findLastOptionIndex():this.findLastFocusedOptionIndex();e.shiftKey&&this.onOptionSelectRange(e,l,this.startRangeIndex),this.changeFocusedOptionIndex(e,l),!this.overlayVisible&&this.show(),e.preventDefault()}},onArrowLeftKey:function(e){var i=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1;i&&(this.focusedOptionIndex=-1)},onHomeKey:function(e){var i=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1;if(i){var l=e.currentTarget;e.shiftKey?l.setSelectionRange(0,e.target.selectionStart):(l.setSelectionRange(0,0),this.focusedOptionIndex=-1)}else{var s=e.metaKey||e.ctrlKey,n=this.findFirstOptionIndex();e.shiftKey&&s&&this.onOptionSelectRange(e,n,this.startRangeIndex),this.changeFocusedOptionIndex(e,n),!this.overlayVisible&&this.show()}e.preventDefault()},onEndKey:function(e){var i=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1;if(i){var l=e.currentTarget;if(e.shiftKey)l.setSelectionRange(e.target.selectionStart,l.value.length);else{var s=l.value.length;l.setSelectionRange(s,s),this.focusedOptionIndex=-1}}else{var n=e.metaKey||e.ctrlKey,c=this.findLastOptionIndex();e.shiftKey&&n&&this.onOptionSelectRange(e,this.startRangeIndex,c),this.changeFocusedOptionIndex(e,c),!this.overlayVisible&&this.show()}e.preventDefault()},onPageUpKey:function(e){this.scrollInView(0),e.preventDefault()},onPageDownKey:function(e){this.scrollInView(this.visibleOptions.length-1),e.preventDefault()},onEnterKey:function(e){this.overlayVisible?this.focusedOptionIndex!==-1&&(e.shiftKey?this.onOptionSelectRange(e,this.focusedOptionIndex):this.onOptionSelect(e,this.visibleOptions[this.focusedOptionIndex])):(this.focusedOptionIndex=-1,this.onArrowDownKey(e)),e.preventDefault()},onEscapeKey:function(e){this.overlayVisible&&this.hide(!0),e.preventDefault()},onTabKey:function(e){var i=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1;i||(this.overlayVisible&&this.hasFocusableElements()?(x(e.shiftKey?this.$refs.lastHiddenFocusableElementOnOverlay:this.$refs.firstHiddenFocusableElementOnOverlay),e.preventDefault()):(this.focusedOptionIndex!==-1&&this.onOptionSelect(e,this.visibleOptions[this.focusedOptionIndex]),this.overlayVisible&&this.hide(this.filter)))},onShiftKey:function(){this.startRangeIndex=this.focusedOptionIndex},onOverlayEnter:function(e){j.set("overlay",e,this.$primevue.config.zIndex.overlay),fe(e,{position:"absolute",top:"0"}),this.alignOverlay(),this.scrollInView(),this.autoFilterFocus&&x(this.$refs.filterInput.$el),this.autoUpdateModel(),this.$attrSelector&&e.setAttribute(this.$attrSelector,"")},onOverlayAfterEnter:function(){this.bindOutsideClickListener(),this.bindScrollListener(),this.bindResizeListener(),this.$emit("show")},onOverlayLeave:function(){this.unbindOutsideClickListener(),this.unbindScrollListener(),this.unbindResizeListener(),this.$emit("hide"),this.overlay=null},onOverlayAfterLeave:function(e){j.clear(e)},alignOverlay:function(){this.appendTo==="self"?ce(this.overlay,this.$el):(this.overlay.style.minWidth=pe(this.$el)+"px",he(this.overlay,this.$el))},bindOutsideClickListener:function(){var e=this;this.outsideClickListener||(this.outsideClickListener=function(i){e.overlayVisible&&e.isOutsideClicked(i)&&e.hide()},document.addEventListener("click",this.outsideClickListener,!0))},unbindOutsideClickListener:function(){this.outsideClickListener&&(document.removeEventListener("click",this.outsideClickListener,!0),this.outsideClickListener=null)},bindScrollListener:function(){var e=this;this.scrollHandler||(this.scrollHandler=new ke(this.$refs.container,function(){e.overlayVisible&&e.hide()})),this.scrollHandler.bindScrollListener()},unbindScrollListener:function(){this.scrollHandler&&this.scrollHandler.unbindScrollListener()},bindResizeListener:function(){var e=this;this.resizeListener||(this.resizeListener=function(){e.overlayVisible&&!ue()&&e.hide()},window.addEventListener("resize",this.resizeListener))},unbindResizeListener:function(){this.resizeListener&&(window.removeEventListener("resize",this.resizeListener),this.resizeListener=null)},isOutsideClicked:function(e){return!(this.$el.isSameNode(e.target)||this.$el.contains(e.target)||this.overlay&&this.overlay.contains(e.target))},getLabelByValue:function(e){var i=this,l=this.optionGroupLabel?this.flatOptions(this.options):this.options||[],s=l.find(function(n){return!i.isOptionGroup(n)&&G(i.getOptionValue(n),e,i.equalityKey)});return s?this.getOptionLabel(s):null},getSelectedItemsLabel:function(){var e=/{(.*?)}/,i=this.selectedItemsLabel||this.$primevue.config.locale.selectionMessage;return e.test(i)?i.replace(i.match(e)[0],this.d_value.length+""):i},onToggleAll:function(e){var i=this;if(this.selectAll!==null)this.$emit("selectall-change",{originalEvent:e,checked:!this.allSelected});else{var l=this.allSelected?[]:this.visibleOptions.filter(function(s){return i.isValidOption(s)}).map(function(s){return i.getOptionValue(s)});this.updateModel(e,l)}},removeOption:function(e,i){var l=this;e.stopPropagation();var s=this.d_value.filter(function(n){return!G(n,i,l.equalityKey)});this.updateModel(e,s)},clearFilter:function(){this.filterValue=null},hasFocusableElements:function(){return de(this.overlay,':not([data-p-hidden-focusable="true"])').length>0},isOptionMatched:function(e){var i;return this.isValidOption(e)&&typeof this.getOptionLabel(e)=="string"&&((i=this.getOptionLabel(e))===null||i===void 0?void 0:i.toLocaleLowerCase(this.filterLocale).startsWith(this.searchValue.toLocaleLowerCase(this.filterLocale)))},isValidOption:function(e){return C(e)&&!(this.isOptionDisabled(e)||this.isOptionGroup(e))},isValidSelectedOption:function(e){return this.isValidOption(e)&&this.isSelected(e)},isEquals:function(e,i){return G(e,i,this.equalityKey)},isSelected:function(e){var i=this,l=this.getOptionValue(e);return(this.d_value||[]).some(function(s){return i.isEquals(s,l)})},findFirstOptionIndex:function(){var e=this;return this.visibleOptions.findIndex(function(i){return e.isValidOption(i)})},findLastOptionIndex:function(){var e=this;return B(this.visibleOptions,function(i){return e.isValidOption(i)})},findNextOptionIndex:function(e){var i=this,l=e<this.visibleOptions.length-1?this.visibleOptions.slice(e+1).findIndex(function(s){return i.isValidOption(s)}):-1;return l>-1?l+e+1:e},findPrevOptionIndex:function(e){var i=this,l=e>0?B(this.visibleOptions.slice(0,e),function(s){return i.isValidOption(s)}):-1;return l>-1?l:e},findSelectedOptionIndex:function(){var e=this;if(this.$filled){for(var i=function(){var c=e.d_value[s],h=e.visibleOptions.findIndex(function(f){return e.isValidSelectedOption(f)&&e.isEquals(c,e.getOptionValue(f))});if(h>-1)return{v:h}},l,s=this.d_value.length-1;s>=0;s--)if(l=i(),l)return l.v}return-1},findFirstSelectedOptionIndex:function(){var e=this;return this.$filled?this.visibleOptions.findIndex(function(i){return e.isValidSelectedOption(i)}):-1},findLastSelectedOptionIndex:function(){var e=this;return this.$filled?B(this.visibleOptions,function(i){return e.isValidSelectedOption(i)}):-1},findNextSelectedOptionIndex:function(e){var i=this,l=this.$filled&&e<this.visibleOptions.length-1?this.visibleOptions.slice(e+1).findIndex(function(s){return i.isValidSelectedOption(s)}):-1;return l>-1?l+e+1:-1},findPrevSelectedOptionIndex:function(e){var i=this,l=this.$filled&&e>0?B(this.visibleOptions.slice(0,e),function(s){return i.isValidSelectedOption(s)}):-1;return l>-1?l:-1},findNearestSelectedOptionIndex:function(e){var i=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1,l=-1;return this.$filled&&(i?(l=this.findPrevSelectedOptionIndex(e),l=l===-1?this.findNextSelectedOptionIndex(e):l):(l=this.findNextSelectedOptionIndex(e),l=l===-1?this.findPrevSelectedOptionIndex(e):l)),l>-1?l:e},findFirstFocusedOptionIndex:function(){var e=this.findFirstSelectedOptionIndex();return e<0?this.findFirstOptionIndex():e},findLastFocusedOptionIndex:function(){var e=this.findSelectedOptionIndex();return e<0?this.findLastOptionIndex():e},searchOptions:function(e){var i=this;this.searchValue=(this.searchValue||"")+e.key;var l=-1;C(this.searchValue)&&(this.focusedOptionIndex!==-1?(l=this.visibleOptions.slice(this.focusedOptionIndex).findIndex(function(s){return i.isOptionMatched(s)}),l=l===-1?this.visibleOptions.slice(0,this.focusedOptionIndex).findIndex(function(s){return i.isOptionMatched(s)}):l+this.focusedOptionIndex):l=this.visibleOptions.findIndex(function(s){return i.isOptionMatched(s)}),l===-1&&this.focusedOptionIndex===-1&&(l=this.findFirstFocusedOptionIndex()),l!==-1&&this.changeFocusedOptionIndex(e,l)),this.searchTimeout&&clearTimeout(this.searchTimeout),this.searchTimeout=setTimeout(function(){i.searchValue="",i.searchTimeout=null},500)},changeFocusedOptionIndex:function(e,i){this.focusedOptionIndex!==i&&(this.focusedOptionIndex=i,this.scrollInView(),this.selectOnFocus&&this.onOptionSelect(e,this.visibleOptions[i]))},scrollInView:function(){var e=this,i=arguments.length>0&&arguments[0]!==void 0?arguments[0]:-1;this.$nextTick(function(){var l=i!==-1?"".concat(e.$id,"_").concat(i):e.focusedOptionId,s=ae(e.list,'li[id="'.concat(l,'"]'));s?s.scrollIntoView&&s.scrollIntoView({block:"nearest",inline:"nearest"}):e.virtualScrollerDisabled||e.virtualScroller&&e.virtualScroller.scrollToIndex(i!==-1?i:e.focusedOptionIndex)})},autoUpdateModel:function(){if(this.autoOptionFocus&&(this.focusedOptionIndex=this.findFirstFocusedOptionIndex()),this.selectOnFocus&&this.autoOptionFocus&&!this.$filled){var e=this.getOptionValue(this.visibleOptions[this.focusedOptionIndex]);this.updateModel(null,[e])}},updateModel:function(e,i){this.writeValue(i,e),this.$emit("change",{originalEvent:e,value:i})},flatOptions:function(e){var i=this;return(e||[]).reduce(function(l,s,n){l.push({optionGroup:s,group:!0,index:n});var c=i.getOptionGroupChildren(s);return c&&c.forEach(function(h){return l.push(h)}),l},[])},overlayRef:function(e){this.overlay=e},listRef:function(e,i){this.list=e,i&&i(e)},virtualScrollerRef:function(e){this.virtualScroller=e}},computed:{visibleOptions:function(){var e=this,i=this.optionGroupLabel?this.flatOptions(this.options):this.options||[];if(this.filterValue){var l=re.filter(i,this.searchFields,this.filterValue,this.filterMatchMode,this.filterLocale);if(this.optionGroupLabel){var s=this.options||[],n=[];return s.forEach(function(c){var h=e.getOptionGroupChildren(c),f=h.filter(function(N){return l.includes(N)});f.length>0&&n.push(Z(Z({},c),{},w({},typeof e.optionGroupChildren=="string"?e.optionGroupChildren:"items",J(f))))}),this.flatOptions(n)}return l}return i},label:function(){var e;if(this.d_value&&this.d_value.length){if(C(this.maxSelectedLabels)&&this.d_value.length>this.maxSelectedLabels)return this.getSelectedItemsLabel();e="";for(var i=0;i<this.d_value.length;i++)i!==0&&(e+=", "),e+=this.getLabelByValue(this.d_value[i])}else e=this.placeholder;return e},chipSelectedItems:function(){return C(this.maxSelectedLabels)&&this.d_value&&this.d_value.length>this.maxSelectedLabels},allSelected:function(){var e=this;return this.selectAll!==null?this.selectAll:C(this.visibleOptions)&&this.visibleOptions.every(function(i){return e.isOptionGroup(i)||e.isOptionDisabled(i)||e.isSelected(i)})},hasSelectedOption:function(){return this.$filled},equalityKey:function(){return this.optionValue?null:this.dataKey},searchFields:function(){return this.filterFields||[this.optionLabel]},maxSelectionLimitReached:function(){return this.selectionLimit&&this.d_value&&this.d_value.length===this.selectionLimit},filterResultMessageText:function(){return C(this.visibleOptions)?this.filterMessageText.replaceAll("{0}",this.visibleOptions.length):this.emptyFilterMessageText},filterMessageText:function(){return this.filterMessage||this.$primevue.config.locale.searchMessage||""},emptyFilterMessageText:function(){return this.emptyFilterMessage||this.$primevue.config.locale.emptySearchMessage||this.$primevue.config.locale.emptyFilterMessage||""},emptyMessageText:function(){return this.emptyMessage||this.$primevue.config.locale.emptyMessage||""},selectionMessageText:function(){return this.selectionMessage||this.$primevue.config.locale.selectionMessage||""},emptySelectionMessageText:function(){return this.emptySelectionMessage||this.$primevue.config.locale.emptySelectionMessage||""},selectedMessageText:function(){return this.$filled?this.selectionMessageText.replaceAll("{0}",this.d_value.length):this.emptySelectionMessageText},focusedOptionId:function(){return this.focusedOptionIndex!==-1?"".concat(this.$id,"_").concat(this.focusedOptionIndex):null},ariaSetSize:function(){var e=this;return this.visibleOptions.filter(function(i){return!e.isOptionGroup(i)}).length},toggleAllAriaLabel:function(){return this.$primevue.config.locale.aria?this.$primevue.config.locale.aria[this.allSelected?"selectAll":"unselectAll"]:void 0},listAriaLabel:function(){return this.$primevue.config.locale.aria?this.$primevue.config.locale.aria.listLabel:void 0},virtualScrollerDisabled:function(){return!this.virtualScrollerOptions},hasFluid:function(){return oe(this.fluid)?!!this.$pcFluid:this.fluid},isClearIconVisible:function(){return this.showClear&&this.d_value&&this.d_value.length&&this.d_value!=null&&C(this.options)},containerDataP:function(){return H(w({invalid:this.$invalid,disabled:this.disabled,focus:this.focused,fluid:this.$fluid,filled:this.$variant==="filled"},this.size,this.size))},labelDataP:function(){return H(w(w(w({placeholder:this.label===this.placeholder,clearable:this.showClear,disabled:this.disabled},this.size,this.size),"has-chip",this.display==="chip"&&this.d_value&&this.d_value.length&&(this.maxSelectedLabels?this.d_value.length<=this.maxSelectedLabels:!0)),"empty",!this.placeholder&&!this.$filled))},dropdownIconDataP:function(){return H(w({},this.size,this.size))},overlayDataP:function(){return H(w({},"portal-"+this.appendTo,"portal-"+this.appendTo))}},directives:{ripple:Pe},components:{InputText:De,Checkbox:Ve,VirtualScroller:Re,Portal:Ee,Chip:Ke,IconField:Te,InputIcon:Me,TimesIcon:xe,SearchIcon:Fe,ChevronDownIcon:Le,SpinnerIcon:Ce,CheckIcon:we}};function P(t){"@babel/helpers - typeof";return P=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},P(t)}function Q(t,e,i){return(e=Xe(e))in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}function Xe(t){var e=Ye(t,"string");return P(e)=="symbol"?e:e+""}function Ye(t,e){if(P(t)!="object"||!t)return t;var i=t[Symbol.toPrimitive];if(i!==void 0){var l=i.call(t,e);if(P(l)!="object")return l;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}var _e=["data-p"],et=["id","disabled","placeholder","tabindex","aria-label","aria-labelledby","aria-expanded","aria-controls","aria-activedescendant","aria-invalid"],tt=["data-p"],it={key:0},nt=["data-p"],lt=["id","aria-label"],st=["id"],ot=["id","aria-label","aria-selected","aria-disabled","aria-setsize","aria-posinset","onClick","onMousemove","data-p-selected","data-p-focused","data-p-disabled"];function rt(t,e,i,l,s,n){var c=I("Chip"),h=I("SpinnerIcon"),f=I("Checkbox"),N=I("InputText"),X=I("SearchIcon"),Y=I("InputIcon"),_=I("IconField"),ee=I("VirtualScroller"),te=I("Portal"),ie=ye("ripple");return r(),u("div",o({ref:"container",class:t.cx("root"),style:t.sx("root"),onClick:e[7]||(e[7]=function(){return n.onContainerClick&&n.onContainerClick.apply(n,arguments)}),"data-p":n.containerDataP},t.ptmi("root")),[y("div",o({class:"p-hidden-accessible"},t.ptm("hiddenInputContainer"),{"data-p-hidden-accessible":!0}),[y("input",o({ref:"focusInput",id:t.inputId,type:"text",readonly:"",disabled:t.disabled,placeholder:t.placeholder,tabindex:t.disabled?-1:t.tabindex,role:"combobox","aria-label":t.ariaLabel,"aria-labelledby":t.ariaLabelledby,"aria-haspopup":"listbox","aria-expanded":s.overlayVisible,"aria-controls":t.$id+"_list","aria-activedescendant":s.focused?n.focusedOptionId:void 0,"aria-invalid":t.invalid||void 0,onFocus:e[0]||(e[0]=function(){return n.onFocus&&n.onFocus.apply(n,arguments)}),onBlur:e[1]||(e[1]=function(){return n.onBlur&&n.onBlur.apply(n,arguments)}),onKeydown:e[2]||(e[2]=function(){return n.onKeyDown&&n.onKeyDown.apply(n,arguments)})},t.ptm("hiddenInput")),null,16,et)],16),y("div",o({class:t.cx("labelContainer")},t.ptm("labelContainer")),[y("div",o({class:t.cx("label"),"data-p":n.labelDataP},t.ptm("label")),[m(t.$slots,"value",{value:t.d_value,placeholder:t.placeholder},function(){return[t.display==="comma"?(r(),u(M,{key:0},[A(v(n.label||"empty"),1)],64)):t.display==="chip"?(r(),u(M,{key:1},[n.chipSelectedItems?(r(),u("span",it,v(n.label),1)):(r(!0),u(M,{key:1},q(t.d_value,function(a){return r(),u("span",o({key:n.getLabelByValue(a),class:t.cx("chipItem")},{ref_for:!0},t.ptm("chipItem")),[m(t.$slots,"chip",{value:a,removeCallback:function(L){return n.removeOption(L,a)}},function(){return[V(c,{class:k(t.cx("pcChip")),label:n.getLabelByValue(a),removeIcon:t.chipIcon||t.removeTokenIcon,removable:"",unstyled:t.unstyled,onRemove:function(L){return n.removeOption(L,a)},pt:t.ptm("pcChip")},{removeicon:S(function(){return[m(t.$slots,t.$slots.chipicon?"chipicon":"removetokenicon",{class:k(t.cx("chipIcon")),item:a,removeCallback:function(L){return n.removeOption(L,a)}})]}),_:2},1032,["class","label","removeIcon","unstyled","onRemove","pt"])]})],16)}),128)),!t.d_value||t.d_value.length===0?(r(),u(M,{key:2},[A(v(t.placeholder||"empty"),1)],64)):g("",!0)],64)):g("",!0)]})],16,tt)],16),n.isClearIconVisible?m(t.$slots,"clearicon",{key:0,class:k(t.cx("clearIcon")),clearCallback:n.onClearClick},function(){return[(r(),O(D(t.clearIcon?"i":"TimesIcon"),o({ref:"clearIcon",class:[t.cx("clearIcon"),t.clearIcon],onClick:n.onClearClick},t.ptm("clearIcon"),{"data-pc-section":"clearicon"}),null,16,["class","onClick"]))]}):g("",!0),y("div",o({class:t.cx("dropdown")},t.ptm("dropdown")),[t.loading?m(t.$slots,"loadingicon",{key:0,class:k(t.cx("loadingIcon"))},function(){return[t.loadingIcon?(r(),u("span",o({key:0,class:[t.cx("loadingIcon"),"pi-spin",t.loadingIcon],"aria-hidden":"true"},t.ptm("loadingIcon")),null,16)):(r(),O(h,o({key:1,class:t.cx("loadingIcon"),spin:"","aria-hidden":"true"},t.ptm("loadingIcon")),null,16,["class"]))]}):m(t.$slots,"dropdownicon",{key:1,class:k(t.cx("dropdownIcon"))},function(){return[(r(),O(D(t.dropdownIcon?"span":"ChevronDownIcon"),o({class:[t.cx("dropdownIcon"),t.dropdownIcon],"aria-hidden":"true","data-p":n.dropdownIconDataP},t.ptm("dropdownIcon")),null,16,["class","data-p"]))]})],16),V(te,{appendTo:t.appendTo},{default:S(function(){return[V(ve,o({name:"p-connected-overlay",onEnter:n.onOverlayEnter,onAfterEnter:n.onOverlayAfterEnter,onLeave:n.onOverlayLeave,onAfterLeave:n.onOverlayAfterLeave},t.ptm("transition")),{default:S(function(){return[s.overlayVisible?(r(),u("div",o({key:0,ref:n.overlayRef,style:[t.panelStyle,t.overlayStyle],class:[t.cx("overlay"),t.panelClass,t.overlayClass],onClick:e[5]||(e[5]=function(){return n.onOverlayClick&&n.onOverlayClick.apply(n,arguments)}),onKeydown:e[6]||(e[6]=function(){return n.onOverlayKeyDown&&n.onOverlayKeyDown.apply(n,arguments)}),"data-p":n.overlayDataP},t.ptm("overlay")),[y("span",o({ref:"firstHiddenFocusableElementOnOverlay",role:"presentation","aria-hidden":"true",class:"p-hidden-accessible p-hidden-focusable",tabindex:0,onFocus:e[3]||(e[3]=function(){return n.onFirstHiddenFocus&&n.onFirstHiddenFocus.apply(n,arguments)})},t.ptm("hiddenFirstFocusableEl"),{"data-p-hidden-accessible":!0,"data-p-hidden-focusable":!0}),null,16),m(t.$slots,"header",{value:t.d_value,options:n.visibleOptions}),t.showToggleAll&&t.selectionLimit==null||t.filter?(r(),u("div",o({key:0,class:t.cx("header")},t.ptm("header")),[t.showToggleAll&&t.selectionLimit==null?(r(),O(f,{key:0,modelValue:n.allSelected,binary:!0,disabled:t.disabled,variant:t.variant,"aria-label":n.toggleAllAriaLabel,onChange:n.onToggleAll,unstyled:t.unstyled,pt:n.getHeaderCheckboxPTOptions("pcHeaderCheckbox"),formControl:{novalidate:!0}},{icon:S(function(a){return[t.$slots.headercheckboxicon?(r(),O(D(t.$slots.headercheckboxicon),{key:0,checked:a.checked,class:k(a.class)},null,8,["checked","class"])):a.checked?(r(),O(D(t.checkboxIcon?"span":"CheckIcon"),o({key:1,class:[a.class,Q({},t.checkboxIcon,a.checked)]},n.getHeaderCheckboxPTOptions("pcHeaderCheckbox.icon")),null,16,["class"])):g("",!0)]}),_:1},8,["modelValue","disabled","variant","aria-label","onChange","unstyled","pt"])):g("",!0),t.filter?(r(),O(_,{key:1,class:k(t.cx("pcFilterContainer")),unstyled:t.unstyled,pt:t.ptm("pcFilterContainer")},{default:S(function(){return[V(N,{ref:"filterInput",value:s.filterValue,onVnodeMounted:n.onFilterUpdated,onVnodeUpdated:n.onFilterUpdated,class:k(t.cx("pcFilter")),placeholder:t.filterPlaceholder,disabled:t.disabled,variant:t.variant,unstyled:t.unstyled,role:"searchbox",autocomplete:"off","aria-owns":t.$id+"_list","aria-activedescendant":n.focusedOptionId,onKeydown:n.onFilterKeyDown,onBlur:n.onFilterBlur,onInput:n.onFilterChange,pt:t.ptm("pcFilter"),formControl:{novalidate:!0}},null,8,["value","onVnodeMounted","onVnodeUpdated","class","placeholder","disabled","variant","unstyled","aria-owns","aria-activedescendant","onKeydown","onBlur","onInput","pt"]),V(Y,{unstyled:t.unstyled,pt:t.ptm("pcFilterIconContainer")},{default:S(function(){return[m(t.$slots,"filtericon",{},function(){return[t.filterIcon?(r(),u("span",o({key:0,class:t.filterIcon},t.ptm("filterIcon")),null,16)):(r(),O(X,Oe(o({key:1},t.ptm("filterIcon"))),null,16))]})]}),_:3},8,["unstyled","pt"])]}),_:3},8,["class","unstyled","pt"])):g("",!0),t.filter?(r(),u("span",o({key:2,role:"status","aria-live":"polite",class:"p-hidden-accessible"},t.ptm("hiddenFilterResult"),{"data-p-hidden-accessible":!0}),v(n.filterResultMessageText),17)):g("",!0)],16)):g("",!0),y("div",o({class:t.cx("listContainer"),style:{"max-height":n.virtualScrollerDisabled?t.scrollHeight:""}},t.ptm("listContainer")),[V(ee,o({ref:n.virtualScrollerRef},t.virtualScrollerOptions,{items:n.visibleOptions,style:{height:t.scrollHeight},tabindex:-1,disabled:n.virtualScrollerDisabled,pt:t.ptm("virtualScroller")}),Ie({content:S(function(a){var K=a.styleClass,L=a.contentRef,R=a.items,b=a.getItemOptions,ne=a.contentStyle,z=a.itemSize;return[y("ul",o({ref:function(p){return n.listRef(p,L)},id:t.$id+"_list",class:[t.cx("list"),K],style:ne,role:"listbox","aria-multiselectable":"true","aria-label":n.listAriaLabel},t.ptm("list")),[(r(!0),u(M,null,q(R,function(d,p){return r(),u(M,{key:n.getOptionRenderKey(d,n.getOptionIndex(p,b))},[n.isOptionGroup(d)?(r(),u("li",o({key:0,id:t.$id+"_"+n.getOptionIndex(p,b),style:{height:z?z+"px":void 0},class:t.cx("optionGroup"),role:"option"},{ref_for:!0},t.ptm("optionGroup")),[m(t.$slots,"optiongroup",{option:d.optionGroup,index:n.getOptionIndex(p,b)},function(){return[A(v(n.getOptionGroupLabel(d.optionGroup)),1)]})],16,st)):Se((r(),u("li",o({key:1,id:t.$id+"_"+n.getOptionIndex(p,b),style:{height:z?z+"px":void 0},class:t.cx("option",{option:d,index:p,getItemOptions:b}),role:"option","aria-label":n.getOptionLabel(d),"aria-selected":n.isSelected(d),"aria-disabled":n.isOptionDisabled(d),"aria-setsize":n.ariaSetSize,"aria-posinset":n.getAriaPosInset(n.getOptionIndex(p,b)),onClick:function(U){return n.onOptionSelect(U,d,n.getOptionIndex(p,b),!0)},onMousemove:function(U){return n.onOptionMouseMove(U,n.getOptionIndex(p,b))}},{ref_for:!0},n.getCheckboxPTOptions(d,b,p,"option"),{"data-p-selected":n.isSelected(d),"data-p-focused":s.focusedOptionIndex===n.getOptionIndex(p,b),"data-p-disabled":n.isOptionDisabled(d)}),[V(f,{defaultValue:n.isSelected(d),binary:!0,tabindex:-1,variant:t.variant,unstyled:t.unstyled,pt:n.getCheckboxPTOptions(d,b,p,"pcOptionCheckbox"),formControl:{novalidate:!0}},{icon:S(function(F){return[t.$slots.optioncheckboxicon||t.$slots.itemcheckboxicon?(r(),O(D(t.$slots.optioncheckboxicon||t.$slots.itemcheckboxicon),{key:0,checked:F.checked,class:k(F.class)},null,8,["checked","class"])):F.checked?(r(),O(D(t.checkboxIcon?"span":"CheckIcon"),o({key:1,class:[F.class,Q({},t.checkboxIcon,F.checked)]},{ref_for:!0},n.getCheckboxPTOptions(d,b,p,"pcOptionCheckbox.icon")),null,16,["class"])):g("",!0)]}),_:2},1032,["defaultValue","variant","unstyled","pt"]),m(t.$slots,"option",{option:d,selected:n.isSelected(d),index:n.getOptionIndex(p,b)},function(){return[y("span",o({ref_for:!0},t.ptm("optionLabel")),v(n.getOptionLabel(d)),17)]})],16,ot)),[[ie]])],64)}),128)),s.filterValue&&(!R||R&&R.length===0)?(r(),u("li",o({key:0,class:t.cx("emptyMessage"),role:"option"},t.ptm("emptyMessage")),[m(t.$slots,"emptyfilter",{},function(){return[A(v(n.emptyFilterMessageText),1)]})],16)):!t.options||t.options&&t.options.length===0?(r(),u("li",o({key:1,class:t.cx("emptyMessage"),role:"option"},t.ptm("emptyMessage")),[m(t.$slots,"empty",{},function(){return[A(v(n.emptyMessageText),1)]})],16)):g("",!0)],16,lt)]}),_:2},[t.$slots.loader?{name:"loader",fn:S(function(a){var K=a.options;return[m(t.$slots,"loader",{options:K})]}),key:"0"}:void 0]),1040,["items","style","disabled","pt"])],16),m(t.$slots,"footer",{value:t.d_value,options:n.visibleOptions}),!t.options||t.options&&t.options.length===0?(r(),u("span",o({key:1,role:"status","aria-live":"polite",class:"p-hidden-accessible"},t.ptm("hiddenEmptyMessage"),{"data-p-hidden-accessible":!0}),v(n.emptyMessageText),17)):g("",!0),y("span",o({role:"status","aria-live":"polite",class:"p-hidden-accessible"},t.ptm("hiddenSelectedMessage"),{"data-p-hidden-accessible":!0}),v(n.selectedMessageText),17),y("span",o({ref:"lastHiddenFocusableElementOnOverlay",role:"presentation","aria-hidden":"true",class:"p-hidden-accessible p-hidden-focusable",tabindex:0,onFocus:e[4]||(e[4]=function(){return n.onLastHiddenFocus&&n.onLastHiddenFocus.apply(n,arguments)})},t.ptm("hiddenLastFocusableEl"),{"data-p-hidden-accessible":!0,"data-p-hidden-focusable":!0}),null,16)],16,nt)):g("",!0)]}),_:3},16,["onEnter","onAfterEnter","onLeave","onAfterLeave"])]}),_:3},8,["appendTo"])],16,_e)}Qe.render=rt;export{Qe as default};
