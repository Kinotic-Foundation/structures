import{c as E}from"./Be1fzYNM.js";import{v as X,B as Y,x as V,y as F,z as _,A as v,C as ee,D as te,E as ie,G as oe,H as ne,I as le,J as re,K as w,L as S,M as ae,c,o as s,q as R,l as O,N as m,b as T,p as se,O as k,P as r,a as C,F as B,s as N,w as x,Q as ue,t as M,T as de,R as pe,S as ce,d as z}from"./DIVREvd8.js";import{Z as j}from"./DGEOgZpe.js";import{C as fe}from"./DcP9kpS0.js";import{s as he}from"./rzT3uIO0.js";import{s as me}from"./D_f71Dqf.js";import ye from"./LIl4s4Ja.js";import ge from"./bJxOl9-S.js";import{O as be}from"./DtO3SA5H.js";import{s as ve}from"./Dk-jlASV.js";import{R as Oe}from"./DHxJ3ARZ.js";import Ie from"./0_fEvNy5.js";import{s as we}from"./jS7rQ_kC.js";import"./fNKMJjk8.js";import"./BOp_CX-A.js";import"./C_T4oPjA.js";import"./zNb_omYy.js";import"./DmfvTtO6.js";import"./DGB91fzl.js";var Se=X`
    .p-autocomplete {
        display: inline-flex;
    }

    .p-autocomplete-loader {
        position: absolute;
        top: 50%;
        margin-top: -0.5rem;
        inset-inline-end: dt('autocomplete.padding.x');
    }

    .p-autocomplete:has(.p-autocomplete-dropdown) .p-autocomplete-loader {
        inset-inline-end: calc(dt('autocomplete.dropdown.width') + dt('autocomplete.padding.x'));
    }

    .p-autocomplete:has(.p-autocomplete-dropdown) .p-autocomplete-input {
        flex: 1 1 auto;
        width: 1%;
    }

    .p-autocomplete:has(.p-autocomplete-dropdown) .p-autocomplete-input,
    .p-autocomplete:has(.p-autocomplete-dropdown) .p-autocomplete-input-multiple {
        border-start-end-radius: 0;
        border-end-end-radius: 0;
    }

    .p-autocomplete-dropdown {
        cursor: pointer;
        display: inline-flex;
        user-select: none;
        align-items: center;
        justify-content: center;
        overflow: hidden;
        position: relative;
        width: dt('autocomplete.dropdown.width');
        border-start-end-radius: dt('autocomplete.dropdown.border.radius');
        border-end-end-radius: dt('autocomplete.dropdown.border.radius');
        background: dt('autocomplete.dropdown.background');
        border: 1px solid dt('autocomplete.dropdown.border.color');
        border-inline-start: 0 none;
        color: dt('autocomplete.dropdown.color');
        transition:
            background dt('autocomplete.transition.duration'),
            color dt('autocomplete.transition.duration'),
            border-color dt('autocomplete.transition.duration'),
            outline-color dt('autocomplete.transition.duration'),
            box-shadow dt('autocomplete.transition.duration');
        outline-color: transparent;
    }

    .p-autocomplete-dropdown:not(:disabled):hover {
        background: dt('autocomplete.dropdown.hover.background');
        border-color: dt('autocomplete.dropdown.hover.border.color');
        color: dt('autocomplete.dropdown.hover.color');
    }

    .p-autocomplete-dropdown:not(:disabled):active {
        background: dt('autocomplete.dropdown.active.background');
        border-color: dt('autocomplete.dropdown.active.border.color');
        color: dt('autocomplete.dropdown.active.color');
    }

    .p-autocomplete-dropdown:focus-visible {
        box-shadow: dt('autocomplete.dropdown.focus.ring.shadow');
        outline: dt('autocomplete.dropdown.focus.ring.width') dt('autocomplete.dropdown.focus.ring.style') dt('autocomplete.dropdown.focus.ring.color');
        outline-offset: dt('autocomplete.dropdown.focus.ring.offset');
    }

    .p-autocomplete .p-autocomplete-overlay {
        min-width: 100%;
    }

    .p-autocomplete-overlay {
        position: absolute;
        top: 0;
        left: 0;
        background: dt('autocomplete.overlay.background');
        color: dt('autocomplete.overlay.color');
        border: 1px solid dt('autocomplete.overlay.border.color');
        border-radius: dt('autocomplete.overlay.border.radius');
        box-shadow: dt('autocomplete.overlay.shadow');
    }

    .p-autocomplete-list-container {
        overflow: auto;
    }

    .p-autocomplete-list {
        margin: 0;
        list-style-type: none;
        display: flex;
        flex-direction: column;
        gap: dt('autocomplete.list.gap');
        padding: dt('autocomplete.list.padding');
    }

    .p-autocomplete-option {
        cursor: pointer;
        white-space: nowrap;
        position: relative;
        overflow: hidden;
        display: flex;
        align-items: center;
        padding: dt('autocomplete.option.padding');
        border: 0 none;
        color: dt('autocomplete.option.color');
        background: transparent;
        transition:
            background dt('autocomplete.transition.duration'),
            color dt('autocomplete.transition.duration'),
            border-color dt('autocomplete.transition.duration');
        border-radius: dt('autocomplete.option.border.radius');
    }

    .p-autocomplete-option:not(.p-autocomplete-option-selected):not(.p-disabled).p-focus {
        background: dt('autocomplete.option.focus.background');
        color: dt('autocomplete.option.focus.color');
    }

    .p-autocomplete-option-selected {
        background: dt('autocomplete.option.selected.background');
        color: dt('autocomplete.option.selected.color');
    }

    .p-autocomplete-option-selected.p-focus {
        background: dt('autocomplete.option.selected.focus.background');
        color: dt('autocomplete.option.selected.focus.color');
    }

    .p-autocomplete-option-group {
        margin: 0;
        padding: dt('autocomplete.option.group.padding');
        color: dt('autocomplete.option.group.color');
        background: dt('autocomplete.option.group.background');
        font-weight: dt('autocomplete.option.group.font.weight');
    }

    .p-autocomplete-input-multiple {
        margin: 0;
        list-style-type: none;
        cursor: text;
        overflow: hidden;
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        padding: calc(dt('autocomplete.padding.y') / 2) dt('autocomplete.padding.x');
        gap: calc(dt('autocomplete.padding.y') / 2);
        color: dt('autocomplete.color');
        background: dt('autocomplete.background');
        border: 1px solid dt('autocomplete.border.color');
        border-radius: dt('autocomplete.border.radius');
        width: 100%;
        transition:
            background dt('autocomplete.transition.duration'),
            color dt('autocomplete.transition.duration'),
            border-color dt('autocomplete.transition.duration'),
            outline-color dt('autocomplete.transition.duration'),
            box-shadow dt('autocomplete.transition.duration');
        outline-color: transparent;
        box-shadow: dt('autocomplete.shadow');
    }

    .p-autocomplete:not(.p-disabled):hover .p-autocomplete-input-multiple {
        border-color: dt('autocomplete.hover.border.color');
    }

    .p-autocomplete:not(.p-disabled).p-focus .p-autocomplete-input-multiple {
        border-color: dt('autocomplete.focus.border.color');
        box-shadow: dt('autocomplete.focus.ring.shadow');
        outline: dt('autocomplete.focus.ring.width') dt('autocomplete.focus.ring.style') dt('autocomplete.focus.ring.color');
        outline-offset: dt('autocomplete.focus.ring.offset');
    }

    .p-autocomplete.p-invalid .p-autocomplete-input-multiple {
        border-color: dt('autocomplete.invalid.border.color');
    }

    .p-variant-filled.p-autocomplete-input-multiple {
        background: dt('autocomplete.filled.background');
    }

    .p-autocomplete:not(.p-disabled):hover .p-variant-filled.p-autocomplete-input-multiple {
        background: dt('autocomplete.filled.hover.background');
    }

    .p-autocomplete:not(.p-disabled).p-focus .p-variant-filled.p-autocomplete-input-multiple {
        background: dt('autocomplete.filled.focus.background');
    }

    .p-autocomplete.p-disabled .p-autocomplete-input-multiple {
        opacity: 1;
        background: dt('autocomplete.disabled.background');
        color: dt('autocomplete.disabled.color');
    }

    .p-autocomplete-chip.p-chip {
        padding-block-start: calc(dt('autocomplete.padding.y') / 2);
        padding-block-end: calc(dt('autocomplete.padding.y') / 2);
        border-radius: dt('autocomplete.chip.border.radius');
    }

    .p-autocomplete-input-multiple:has(.p-autocomplete-chip) {
        padding-inline-start: calc(dt('autocomplete.padding.y') / 2);
        padding-inline-end: calc(dt('autocomplete.padding.y') / 2);
    }

    .p-autocomplete-chip-item.p-focus .p-autocomplete-chip {
        background: dt('autocomplete.chip.focus.background');
        color: dt('autocomplete.chip.focus.color');
    }

    .p-autocomplete-input-chip {
        flex: 1 1 auto;
        display: inline-flex;
        padding-block-start: calc(dt('autocomplete.padding.y') / 2);
        padding-block-end: calc(dt('autocomplete.padding.y') / 2);
    }

    .p-autocomplete-input-chip input {
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

    .p-autocomplete-input-chip input::placeholder {
        color: dt('autocomplete.placeholder.color');
    }

    .p-autocomplete.p-invalid .p-autocomplete-input-chip input::placeholder {
        color: dt('autocomplete.invalid.placeholder.color');
    }

    .p-autocomplete-empty-message {
        padding: dt('autocomplete.empty.message.padding');
    }

    .p-autocomplete-fluid {
        display: flex;
    }

    .p-autocomplete-fluid:has(.p-autocomplete-dropdown) .p-autocomplete-input {
        width: 1%;
    }

    .p-autocomplete:has(.p-inputtext-sm) .p-autocomplete-dropdown {
        width: dt('autocomplete.dropdown.sm.width');
    }

    .p-autocomplete:has(.p-inputtext-sm) .p-autocomplete-dropdown .p-icon {
        font-size: dt('form.field.sm.font.size');
        width: dt('form.field.sm.font.size');
        height: dt('form.field.sm.font.size');
    }

    .p-autocomplete:has(.p-inputtext-lg) .p-autocomplete-dropdown {
        width: dt('autocomplete.dropdown.lg.width');
    }

    .p-autocomplete:has(.p-inputtext-lg) .p-autocomplete-dropdown .p-icon {
        font-size: dt('form.field.lg.font.size');
        width: dt('form.field.lg.font.size');
        height: dt('form.field.lg.font.size');
    }
`,ke={root:{position:"relative"}},Ce={root:function(e){var i=e.instance,n=e.props;return["p-autocomplete p-component p-inputwrapper",{"p-disabled":n.disabled,"p-invalid":i.$invalid,"p-focus":i.focused,"p-inputwrapper-filled":i.$filled||V(i.inputValue),"p-inputwrapper-focus":i.focused,"p-autocomplete-open":i.overlayVisible,"p-autocomplete-fluid":i.$fluid}]},pcInputText:"p-autocomplete-input",inputMultiple:function(e){var i=e.instance;return["p-autocomplete-input-multiple",{"p-variant-filled":i.$variant==="filled"}]},chipItem:function(e){var i=e.instance,n=e.i;return["p-autocomplete-chip-item",{"p-focus":i.focusedMultipleOptionIndex===n}]},pcChip:"p-autocomplete-chip",chipIcon:"p-autocomplete-chip-icon",inputChip:"p-autocomplete-input-chip",loader:"p-autocomplete-loader",dropdown:"p-autocomplete-dropdown",overlay:"p-autocomplete-overlay p-component",listContainer:"p-autocomplete-list-container",list:"p-autocomplete-list",optionGroup:"p-autocomplete-option-group",option:function(e){var i=e.instance,n=e.option,l=e.i,o=e.getItemOptions;return["p-autocomplete-option",{"p-autocomplete-option-selected":i.isSelected(n),"p-focus":i.focusedOptionIndex===i.getOptionIndex(l,o),"p-disabled":i.isOptionDisabled(n)}]},emptyMessage:"p-autocomplete-empty-message"},xe=Y.extend({name:"autocomplete",style:Se,classes:Ce,inlineStyles:ke}),Me={name:"BaseAutoComplete",extends:we,props:{suggestions:{type:Array,default:null},optionLabel:null,optionDisabled:null,optionGroupLabel:null,optionGroupChildren:null,scrollHeight:{type:String,default:"14rem"},dropdown:{type:Boolean,default:!1},dropdownMode:{type:String,default:"blank"},multiple:{type:Boolean,default:!1},loading:{type:Boolean,default:!1},placeholder:{type:String,default:null},dataKey:{type:String,default:null},minLength:{type:Number,default:1},delay:{type:Number,default:300},appendTo:{type:[String,Object],default:"body"},forceSelection:{type:Boolean,default:!1},completeOnFocus:{type:Boolean,default:!1},inputId:{type:String,default:null},inputStyle:{type:Object,default:null},inputClass:{type:[String,Object],default:null},panelStyle:{type:Object,default:null},panelClass:{type:[String,Object],default:null},overlayStyle:{type:Object,default:null},overlayClass:{type:[String,Object],default:null},dropdownIcon:{type:String,default:null},dropdownClass:{type:[String,Object],default:null},loader:{type:String,default:null},loadingIcon:{type:String,default:null},removeTokenIcon:{type:String,default:null},chipIcon:{type:String,default:null},virtualScrollerOptions:{type:Object,default:null},autoOptionFocus:{type:Boolean,default:!1},selectOnFocus:{type:Boolean,default:!1},focusOnHover:{type:Boolean,default:!0},searchLocale:{type:String,default:void 0},searchMessage:{type:String,default:null},selectionMessage:{type:String,default:null},emptySelectionMessage:{type:String,default:null},emptySearchMessage:{type:String,default:null},showEmptyMessage:{type:Boolean,default:!0},tabindex:{type:Number,default:0},typeahead:{type:Boolean,default:!0},ariaLabel:{type:String,default:null},ariaLabelledby:{type:String,default:null}},style:xe,provide:function(){return{$pcAutoComplete:this,$parentInstance:this}}};function H(t,e,i){return(e=Le(e))in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}function Le(t){var e=Ke(t,"string");return I(e)=="symbol"?e:e+""}function Ke(t,e){if(I(t)!="object"||!t)return t;var i=t[Symbol.toPrimitive];if(i!==void 0){var n=i.call(t,e);if(I(n)!="object")return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}function I(t){"@babel/helpers - typeof";return I=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},I(t)}function U(t){return Ae(t)||Ve(t)||Te(t)||De()}function De(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function Te(t,e){if(t){if(typeof t=="string")return G(t,e);var i={}.toString.call(t).slice(8,-1);return i==="Object"&&t.constructor&&(i=t.constructor.name),i==="Map"||i==="Set"?Array.from(t):i==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(i)?G(t,e):void 0}}function Ve(t){if(typeof Symbol<"u"&&t[Symbol.iterator]!=null||t["@@iterator"]!=null)return Array.from(t)}function Ae(t){if(Array.isArray(t))return G(t)}function G(t,e){(e==null||e>t.length)&&(e=t.length);for(var i=0,n=Array(e);i<e;i++)n[i]=t[i];return n}var Pe={name:"AutoComplete",extends:Me,inheritAttrs:!1,emits:["change","focus","blur","item-select","item-unselect","option-select","option-unselect","dropdown-click","clear","complete","before-show","before-hide","show","hide"],inject:{$pcFluid:{default:null}},outsideClickListener:null,resizeListener:null,scrollHandler:null,overlay:null,virtualScroller:null,searchTimeout:null,dirty:!1,startRangeIndex:-1,data:function(){return{clicked:!1,focused:!1,focusedOptionIndex:-1,focusedMultipleOptionIndex:-1,overlayVisible:!1,searching:!1}},watch:{suggestions:function(){this.searching&&(this.show(),this.focusedOptionIndex=this.overlayVisible&&this.autoOptionFocus?this.findFirstFocusedOptionIndex():-1,this.searching=!1,!this.showEmptyMessage&&this.visibleOptions.length===0&&this.hide()),this.autoUpdateModel()}},mounted:function(){this.autoUpdateModel()},updated:function(){this.overlayVisible&&this.alignOverlay()},beforeUnmount:function(){this.unbindOutsideClickListener(),this.unbindResizeListener(),this.scrollHandler&&(this.scrollHandler.destroy(),this.scrollHandler=null),this.overlay&&(j.clear(this.overlay),this.overlay=null)},methods:{getOptionIndex:function(e,i){return this.virtualScrollerDisabled?e:i&&i(e).index},getOptionLabel:function(e){return this.optionLabel?w(e,this.optionLabel):e},getOptionValue:function(e){return e},getOptionRenderKey:function(e,i){return(this.dataKey?w(e,this.dataKey):this.getOptionLabel(e))+"_"+i},getPTOptions:function(e,i,n,l){return this.ptm(l,{context:{option:e,index:n,selected:this.isSelected(e),focused:this.focusedOptionIndex===this.getOptionIndex(n,i),disabled:this.isOptionDisabled(e)}})},isOptionDisabled:function(e){return this.optionDisabled?w(e,this.optionDisabled):!1},isOptionGroup:function(e){return this.optionGroupLabel&&e.optionGroup&&e.group},getOptionGroupLabel:function(e){return w(e,this.optionGroupLabel)},getOptionGroupChildren:function(e){return w(e,this.optionGroupChildren)},getAriaPosInset:function(e){var i=this;return(this.optionGroupLabel?e-this.visibleOptions.slice(0,e).filter(function(n){return i.isOptionGroup(n)}).length:e)+1},show:function(e){this.$emit("before-show"),this.dirty=!0,this.overlayVisible=!0,this.focusedOptionIndex=this.focusedOptionIndex!==-1?this.focusedOptionIndex:this.autoOptionFocus?this.findFirstFocusedOptionIndex():-1,e&&v(this.multiple?this.$refs.focusInput:this.$refs.focusInput.$el)},hide:function(e){var i=this,n=function(){var o;i.$emit("before-hide"),i.dirty=e,i.overlayVisible=!1,i.clicked=!1,i.focusedOptionIndex=-1,e&&v(i.multiple?i.$refs.focusInput:(o=i.$refs.focusInput)===null||o===void 0?void 0:o.$el)};setTimeout(function(){n()},0)},onFocus:function(e){this.disabled||(!this.dirty&&this.completeOnFocus&&this.search(e,e.target.value,"focus"),this.dirty=!0,this.focused=!0,this.overlayVisible&&(this.focusedOptionIndex=this.focusedOptionIndex!==-1?this.focusedOptionIndex:this.overlayVisible&&this.autoOptionFocus?this.findFirstFocusedOptionIndex():-1,this.scrollInView(this.focusedOptionIndex)),this.$emit("focus",e))},onBlur:function(e){var i,n;this.dirty=!1,this.focused=!1,this.focusedOptionIndex=-1,this.$emit("blur",e),(i=(n=this.formField).onBlur)===null||i===void 0||i.call(n)},onKeyDown:function(e){if(this.disabled){e.preventDefault();return}switch(e.code){case"ArrowDown":this.onArrowDownKey(e);break;case"ArrowUp":this.onArrowUpKey(e);break;case"ArrowLeft":this.onArrowLeftKey(e);break;case"ArrowRight":this.onArrowRightKey(e);break;case"Home":this.onHomeKey(e);break;case"End":this.onEndKey(e);break;case"PageDown":this.onPageDownKey(e);break;case"PageUp":this.onPageUpKey(e);break;case"Enter":case"NumpadEnter":this.onEnterKey(e);break;case"Space":this.onSpaceKey(e);break;case"Escape":this.onEscapeKey(e);break;case"Tab":this.onTabKey(e);break;case"ShiftLeft":case"ShiftRight":this.onShiftKey(e);break;case"Backspace":this.onBackspaceKey(e);break}this.clicked=!1},onInput:function(e){var i=this;if(this.typeahead){this.searchTimeout&&clearTimeout(this.searchTimeout);var n=e.target.value;this.multiple||this.updateModel(e,n),n.length===0?(this.hide(),this.$emit("clear")):n.length>=this.minLength?(this.focusedOptionIndex=-1,this.searchTimeout=setTimeout(function(){i.search(e,n,"input")},this.delay)):this.hide()}},onChange:function(e){var i=this;if(this.forceSelection){var n=!1;if(this.visibleOptions&&!this.multiple){var l,o=this.multiple?this.$refs.focusInput.value:(l=this.$refs.focusInput)===null||l===void 0||(l=l.$el)===null||l===void 0?void 0:l.value,p=this.visibleOptions.find(function(A){return i.isOptionMatched(A,o||"")});p!==void 0&&(n=!0,!this.isSelected(p)&&this.onOptionSelect(e,p))}if(!n){if(this.multiple)this.$refs.focusInput.value="";else{var h,b=(h=this.$refs.focusInput)===null||h===void 0?void 0:h.$el;b&&(b.value="")}this.$emit("clear"),!this.multiple&&this.updateModel(e,null)}}},onMultipleContainerFocus:function(){this.disabled||(this.focused=!0)},onMultipleContainerBlur:function(){this.focusedMultipleOptionIndex=-1,this.focused=!1},onMultipleContainerKeyDown:function(e){if(this.disabled){e.preventDefault();return}switch(e.code){case"ArrowLeft":this.onArrowLeftKeyOnMultiple(e);break;case"ArrowRight":this.onArrowRightKeyOnMultiple(e);break;case"Backspace":this.onBackspaceKeyOnMultiple(e);break}},onContainerClick:function(e){this.clicked=!0,!(this.disabled||this.searching||this.loading||this.isDropdownClicked(e))&&(!this.overlay||!this.overlay.contains(e.target))&&v(this.multiple?this.$refs.focusInput:this.$refs.focusInput.$el)},onDropdownClick:function(e){var i=void 0;if(this.overlayVisible)this.hide(!0);else{var n=this.multiple?this.$refs.focusInput:this.$refs.focusInput.$el;v(n),i=n.value,this.dropdownMode==="blank"?this.search(e,"","dropdown"):this.dropdownMode==="current"&&this.search(e,i,"dropdown")}this.$emit("dropdown-click",{originalEvent:e,query:i})},onOptionSelect:function(e,i){var n=arguments.length>2&&arguments[2]!==void 0?arguments[2]:!0,l=this.getOptionValue(i);this.multiple?(this.$refs.focusInput.value="",this.isSelected(i)||this.updateModel(e,[].concat(U(this.d_value||[]),[l]))):this.updateModel(e,l),this.$emit("item-select",{originalEvent:e,value:i}),this.$emit("option-select",{originalEvent:e,value:i}),n&&this.hide(!0)},onOptionMouseMove:function(e,i){this.focusOnHover&&this.changeFocusedOptionIndex(e,i)},onOptionSelectRange:function(e){var i=this,n=arguments.length>1&&arguments[1]!==void 0?arguments[1]:-1,l=arguments.length>2&&arguments[2]!==void 0?arguments[2]:-1;if(n===-1&&(n=this.findNearestSelectedOptionIndex(l,!0)),l===-1&&(l=this.findNearestSelectedOptionIndex(n)),n!==-1&&l!==-1){var o=Math.min(n,l),p=Math.max(n,l),h=this.visibleOptions.slice(o,p+1).filter(function(b){return i.isValidOption(b)}).map(function(b){return i.getOptionValue(b)});this.updateModel(e,h)}},onOverlayClick:function(e){be.emit("overlay-click",{originalEvent:e,target:this.$el})},onOverlayKeyDown:function(e){switch(e.code){case"Escape":this.onEscapeKey(e);break}},onArrowDownKey:function(e){if(this.overlayVisible){var i=this.focusedOptionIndex!==-1?this.findNextOptionIndex(this.focusedOptionIndex):this.clicked?this.findFirstOptionIndex():this.findFirstFocusedOptionIndex();this.multiple&&e.shiftKey&&this.onOptionSelectRange(e,this.startRangeIndex,i),this.changeFocusedOptionIndex(e,i),e.preventDefault()}},onArrowUpKey:function(e){if(this.overlayVisible)if(e.altKey)this.focusedOptionIndex!==-1&&this.onOptionSelect(e,this.visibleOptions[this.focusedOptionIndex]),this.overlayVisible&&this.hide(),e.preventDefault();else{var i=this.focusedOptionIndex!==-1?this.findPrevOptionIndex(this.focusedOptionIndex):this.clicked?this.findLastOptionIndex():this.findLastFocusedOptionIndex();this.multiple&&e.shiftKey&&this.onOptionSelectRange(e,i,this.startRangeIndex),this.changeFocusedOptionIndex(e,i),e.preventDefault()}},onArrowLeftKey:function(e){var i=e.currentTarget;this.focusedOptionIndex=-1,this.multiple&&(re(i.value)&&this.$filled?(v(this.$refs.multiContainer),this.focusedMultipleOptionIndex=this.d_value.length):e.stopPropagation())},onArrowRightKey:function(e){this.focusedOptionIndex=-1,this.multiple&&e.stopPropagation()},onHomeKey:function(e){var i=e.currentTarget,n=i.value.length,l=e.metaKey||e.ctrlKey,o=this.findFirstOptionIndex();this.multiple&&e.shiftKey&&l&&this.onOptionSelectRange(e,o,this.startRangeIndex),i.setSelectionRange(0,e.shiftKey?n:0),this.focusedOptionIndex=-1,e.preventDefault()},onEndKey:function(e){var i=e.currentTarget,n=i.value.length,l=e.metaKey||e.ctrlKey,o=this.findLastOptionIndex();this.multiple&&e.shiftKey&&l&&this.onOptionSelectRange(e,this.startRangeIndex,o),i.setSelectionRange(e.shiftKey?0:n,n),this.focusedOptionIndex=-1,e.preventDefault()},onPageUpKey:function(e){this.scrollInView(0),e.preventDefault()},onPageDownKey:function(e){this.scrollInView(this.visibleOptions.length-1),e.preventDefault()},onEnterKey:function(e){this.typeahead?this.overlayVisible?(this.focusedOptionIndex!==-1&&(this.multiple&&e.shiftKey?(this.onOptionSelectRange(e,this.focusedOptionIndex),e.preventDefault()):this.onOptionSelect(e,this.visibleOptions[this.focusedOptionIndex])),this.hide()):(this.focusedOptionIndex=-1,this.onArrowDownKey(e)):this.multiple&&e.target.value.trim()&&(this.updateModel(e,[].concat(U(this.d_value||[]),[e.target.value.trim()])),this.$refs.focusInput.value="")},onSpaceKey:function(e){this.focusedOptionIndex!==-1&&this.onEnterKey(e)},onEscapeKey:function(e){this.overlayVisible&&this.hide(!0),e.preventDefault()},onTabKey:function(e){this.focusedOptionIndex!==-1&&this.onOptionSelect(e,this.visibleOptions[this.focusedOptionIndex]),this.overlayVisible&&this.hide()},onShiftKey:function(){this.startRangeIndex=this.focusedOptionIndex},onBackspaceKey:function(e){if(this.multiple){if(V(this.d_value)&&!this.$refs.focusInput.value){var i=this.d_value[this.d_value.length-1],n=this.d_value.slice(0,-1);this.writeValue(n,e),this.$emit("item-unselect",{originalEvent:e,value:i}),this.$emit("option-unselect",{originalEvent:e,value:i})}e.stopPropagation()}},onArrowLeftKeyOnMultiple:function(){this.focusedMultipleOptionIndex=this.focusedMultipleOptionIndex<1?0:this.focusedMultipleOptionIndex-1},onArrowRightKeyOnMultiple:function(){this.focusedMultipleOptionIndex++,this.focusedMultipleOptionIndex>this.d_value.length-1&&(this.focusedMultipleOptionIndex=-1,v(this.$refs.focusInput))},onBackspaceKeyOnMultiple:function(e){this.focusedMultipleOptionIndex!==-1&&this.removeOption(e,this.focusedMultipleOptionIndex)},onOverlayEnter:function(e){j.set("overlay",e,this.$primevue.config.zIndex.overlay),le(e,{position:"absolute",top:"0"}),this.alignOverlay(),this.$attrSelector&&e.setAttribute(this.$attrSelector,"")},onOverlayAfterEnter:function(){this.bindOutsideClickListener(),this.bindScrollListener(),this.bindResizeListener(),this.$emit("show")},onOverlayLeave:function(){this.unbindOutsideClickListener(),this.unbindScrollListener(),this.unbindResizeListener(),this.$emit("hide"),this.overlay=null},onOverlayAfterLeave:function(e){j.clear(e)},alignOverlay:function(){var e=this.multiple?this.$refs.multiContainer:this.$refs.focusInput.$el;this.appendTo==="self"?ie(this.overlay,e):(this.overlay.style.minWidth=oe(e)+"px",ne(this.overlay,e))},bindOutsideClickListener:function(){var e=this;this.outsideClickListener||(this.outsideClickListener=function(i){e.overlayVisible&&e.overlay&&e.isOutsideClicked(i)&&e.hide()},document.addEventListener("click",this.outsideClickListener,!0))},unbindOutsideClickListener:function(){this.outsideClickListener&&(document.removeEventListener("click",this.outsideClickListener,!0),this.outsideClickListener=null)},bindScrollListener:function(){var e=this;this.scrollHandler||(this.scrollHandler=new fe(this.$refs.container,function(){e.overlayVisible&&e.hide()})),this.scrollHandler.bindScrollListener()},unbindScrollListener:function(){this.scrollHandler&&this.scrollHandler.unbindScrollListener()},bindResizeListener:function(){var e=this;this.resizeListener||(this.resizeListener=function(){e.overlayVisible&&!te()&&e.hide()},window.addEventListener("resize",this.resizeListener))},unbindResizeListener:function(){this.resizeListener&&(window.removeEventListener("resize",this.resizeListener),this.resizeListener=null)},isOutsideClicked:function(e){return!this.overlay.contains(e.target)&&!this.isInputClicked(e)&&!this.isDropdownClicked(e)},isInputClicked:function(e){return this.multiple?e.target===this.$refs.multiContainer||this.$refs.multiContainer.contains(e.target):e.target===this.$refs.focusInput.$el},isDropdownClicked:function(e){return this.$refs.dropdownButton?e.target===this.$refs.dropdownButton||this.$refs.dropdownButton.contains(e.target):!1},isOptionMatched:function(e,i){var n;return this.isValidOption(e)&&((n=this.getOptionLabel(e))===null||n===void 0?void 0:n.toLocaleLowerCase(this.searchLocale))===i.toLocaleLowerCase(this.searchLocale)},isValidOption:function(e){return V(e)&&!(this.isOptionDisabled(e)||this.isOptionGroup(e))},isValidSelectedOption:function(e){return this.isValidOption(e)&&this.isSelected(e)},isEquals:function(e,i){return ee(e,i,this.equalityKey)},isSelected:function(e){var i=this,n=this.getOptionValue(e);return this.multiple?(this.d_value||[]).some(function(l){return i.isEquals(l,n)}):this.isEquals(this.d_value,this.getOptionValue(e))},findFirstOptionIndex:function(){var e=this;return this.visibleOptions.findIndex(function(i){return e.isValidOption(i)})},findLastOptionIndex:function(){var e=this;return F(this.visibleOptions,function(i){return e.isValidOption(i)})},findNextOptionIndex:function(e){var i=this,n=e<this.visibleOptions.length-1?this.visibleOptions.slice(e+1).findIndex(function(l){return i.isValidOption(l)}):-1;return n>-1?n+e+1:e},findPrevOptionIndex:function(e){var i=this,n=e>0?F(this.visibleOptions.slice(0,e),function(l){return i.isValidOption(l)}):-1;return n>-1?n:e},findSelectedOptionIndex:function(){var e=this;return this.$filled?this.visibleOptions.findIndex(function(i){return e.isValidSelectedOption(i)}):-1},findFirstFocusedOptionIndex:function(){var e=this.findSelectedOptionIndex();return e<0?this.findFirstOptionIndex():e},findLastFocusedOptionIndex:function(){var e=this.findSelectedOptionIndex();return e<0?this.findLastOptionIndex():e},search:function(e,i,n){i!=null&&(n==="input"&&i.trim().length===0||(this.searching=!0,this.$emit("complete",{originalEvent:e,query:i})))},removeOption:function(e,i){var n=this,l=this.d_value[i],o=this.d_value.filter(function(p,h){return h!==i}).map(function(p){return n.getOptionValue(p)});this.updateModel(e,o),this.$emit("item-unselect",{originalEvent:e,value:l}),this.$emit("option-unselect",{originalEvent:e,value:l}),this.dirty=!0,v(this.multiple?this.$refs.focusInput:this.$refs.focusInput.$el)},changeFocusedOptionIndex:function(e,i){this.focusedOptionIndex!==i&&(this.focusedOptionIndex=i,this.scrollInView(),this.selectOnFocus&&this.onOptionSelect(e,this.visibleOptions[i],!1))},scrollInView:function(){var e=this,i=arguments.length>0&&arguments[0]!==void 0?arguments[0]:-1;this.$nextTick(function(){var n=i!==-1?"".concat(e.$id,"_").concat(i):e.focusedOptionId,l=_(e.list,'li[id="'.concat(n,'"]'));l?l.scrollIntoView&&l.scrollIntoView({block:"nearest",inline:"start"}):e.virtualScrollerDisabled||e.virtualScroller&&e.virtualScroller.scrollToIndex(i!==-1?i:e.focusedOptionIndex)})},autoUpdateModel:function(){this.selectOnFocus&&this.autoOptionFocus&&!this.$filled&&(this.focusedOptionIndex=this.findFirstFocusedOptionIndex(),this.onOptionSelect(null,this.visibleOptions[this.focusedOptionIndex],!1))},updateModel:function(e,i){this.writeValue(i,e),this.$emit("change",{originalEvent:e,value:i})},flatOptions:function(e){var i=this;return(e||[]).reduce(function(n,l,o){n.push({optionGroup:l,group:!0,index:o});var p=i.getOptionGroupChildren(l);return p&&p.forEach(function(h){return n.push(h)}),n},[])},overlayRef:function(e){this.overlay=e},listRef:function(e,i){this.list=e,i&&i(e)},virtualScrollerRef:function(e){this.virtualScroller=e},findNextSelectedOptionIndex:function(e){var i=this,n=this.$filled&&e<this.visibleOptions.length-1?this.visibleOptions.slice(e+1).findIndex(function(l){return i.isValidSelectedOption(l)}):-1;return n>-1?n+e+1:-1},findPrevSelectedOptionIndex:function(e){var i=this,n=this.$filled&&e>0?F(this.visibleOptions.slice(0,e),function(l){return i.isValidSelectedOption(l)}):-1;return n>-1?n:-1},findNearestSelectedOptionIndex:function(e){var i=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1,n=-1;return this.$filled&&(i?(n=this.findPrevSelectedOptionIndex(e),n=n===-1?this.findNextSelectedOptionIndex(e):n):(n=this.findNextSelectedOptionIndex(e),n=n===-1?this.findPrevSelectedOptionIndex(e):n)),n>-1?n:e}},computed:{visibleOptions:function(){return this.optionGroupLabel?this.flatOptions(this.suggestions):this.suggestions||[]},inputValue:function(){if(this.$filled)if(I(this.d_value)==="object"){var e=this.getOptionLabel(this.d_value);return e??this.d_value}else return this.d_value;else return""},hasSelectedOption:function(){return this.$filled},equalityKey:function(){return this.dataKey},searchResultMessageText:function(){return V(this.visibleOptions)&&this.overlayVisible?this.searchMessageText.replaceAll("{0}",this.visibleOptions.length):this.emptySearchMessageText},searchMessageText:function(){return this.searchMessage||this.$primevue.config.locale.searchMessage||""},emptySearchMessageText:function(){return this.emptySearchMessage||this.$primevue.config.locale.emptySearchMessage||""},selectionMessageText:function(){return this.selectionMessage||this.$primevue.config.locale.selectionMessage||""},emptySelectionMessageText:function(){return this.emptySelectionMessage||this.$primevue.config.locale.emptySelectionMessage||""},selectedMessageText:function(){return this.$filled?this.selectionMessageText.replaceAll("{0}",this.multiple?this.d_value.length:"1"):this.emptySelectionMessageText},listAriaLabel:function(){return this.$primevue.config.locale.aria?this.$primevue.config.locale.aria.listLabel:void 0},focusedOptionId:function(){return this.focusedOptionIndex!==-1?"".concat(this.$id,"_").concat(this.focusedOptionIndex):null},focusedMultipleOptionId:function(){return this.focusedMultipleOptionIndex!==-1?"".concat(this.$id,"_multiple_option_").concat(this.focusedMultipleOptionIndex):null},ariaSetSize:function(){var e=this;return this.visibleOptions.filter(function(i){return!e.isOptionGroup(i)}).length},virtualScrollerDisabled:function(){return!this.virtualScrollerOptions},panelId:function(){return this.$id+"_panel"},containerDataP:function(){return E({fluid:this.$fluid})},overlayDataP:function(){return E(H({},"portal-"+this.appendTo,"portal-"+this.appendTo))},inputMultipleDataP:function(){return E(H({invalid:this.$invalid,disabled:this.disabled,focus:this.focused,fluid:this.$fluid,filled:this.$variant==="filled",empty:!this.$filled},this.size,this.size))}},components:{InputText:ge,VirtualScroller:Ie,Portal:ve,ChevronDownIcon:he,SpinnerIcon:me,Chip:ye},directives:{ripple:Oe}};function L(t){"@babel/helpers - typeof";return L=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},L(t)}function $(t,e){var i=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter(function(l){return Object.getOwnPropertyDescriptor(t,l).enumerable})),i.push.apply(i,n)}return i}function q(t){for(var e=1;e<arguments.length;e++){var i=arguments[e]!=null?arguments[e]:{};e%2?$(Object(i),!0).forEach(function(n){Ee(t,n,i[n])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(i)):$(Object(i)).forEach(function(n){Object.defineProperty(t,n,Object.getOwnPropertyDescriptor(i,n))})}return t}function Ee(t,e,i){return(e=Fe(e))in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}function Fe(t){var e=Re(t,"string");return L(e)=="symbol"?e:e+""}function Re(t,e){if(L(t)!="object"||!t)return t;var i=t[Symbol.toPrimitive];if(i!==void 0){var n=i.call(t,e);if(L(n)!="object")return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}var Be=["data-p"],ze=["aria-activedescendant","data-p-has-dropdown","data-p"],je=["id","aria-label","aria-setsize","aria-posinset"],Ge=["id","placeholder","tabindex","disabled","aria-label","aria-labelledby","aria-expanded","aria-controls","aria-activedescendant","aria-invalid"],Ne=["data-p-has-dropdown"],He=["disabled","aria-expanded","aria-controls"],Ue=["id","data-p"],$e=["id","aria-label"],qe=["id"],We=["id","aria-label","aria-selected","aria-disabled","aria-setsize","aria-posinset","onClick","onMousemove","data-p-selected","data-p-focused","data-p-disabled"];function Ze(t,e,i,n,l,o){var p=S("InputText"),h=S("Chip"),b=S("SpinnerIcon"),A=S("VirtualScroller"),W=S("Portal"),Z=ae("ripple");return s(),c("div",r({ref:"container",class:t.cx("root"),style:t.sx("root"),onClick:e[11]||(e[11]=function(){return o.onContainerClick&&o.onContainerClick.apply(o,arguments)}),"data-p":o.containerDataP},t.ptmi("root")),[t.multiple?O("",!0):(s(),R(p,{key:0,ref:"focusInput",id:t.inputId,type:"text",name:t.$formName,class:k([t.cx("pcInputText"),t.inputClass]),style:se(t.inputStyle),defaultValue:o.inputValue,placeholder:t.placeholder,tabindex:t.disabled?-1:t.tabindex,fluid:t.$fluid,disabled:t.disabled,size:t.size,invalid:t.invalid,variant:t.variant,autocomplete:"off",role:"combobox","aria-label":t.ariaLabel,"aria-labelledby":t.ariaLabelledby,"aria-haspopup":"listbox","aria-autocomplete":"list","aria-expanded":l.overlayVisible,"aria-controls":o.panelId,"aria-activedescendant":l.focused?o.focusedOptionId:void 0,onFocus:o.onFocus,onBlur:o.onBlur,onKeydown:o.onKeyDown,onInput:o.onInput,onChange:o.onChange,unstyled:t.unstyled,"data-p-has-dropdown":t.dropdown,pt:t.ptm("pcInputText")},null,8,["id","name","class","style","defaultValue","placeholder","tabindex","fluid","disabled","size","invalid","variant","aria-label","aria-labelledby","aria-expanded","aria-controls","aria-activedescendant","onFocus","onBlur","onKeydown","onInput","onChange","unstyled","data-p-has-dropdown","pt"])),t.multiple?(s(),c("ul",r({key:1,ref:"multiContainer",class:t.cx("inputMultiple"),tabindex:"-1",role:"listbox","aria-orientation":"horizontal","aria-activedescendant":l.focused?o.focusedMultipleOptionId:void 0,onFocus:e[5]||(e[5]=function(){return o.onMultipleContainerFocus&&o.onMultipleContainerFocus.apply(o,arguments)}),onBlur:e[6]||(e[6]=function(){return o.onMultipleContainerBlur&&o.onMultipleContainerBlur.apply(o,arguments)}),onKeydown:e[7]||(e[7]=function(){return o.onMultipleContainerKeyDown&&o.onMultipleContainerKeyDown.apply(o,arguments)}),"data-p-has-dropdown":t.dropdown,"data-p":o.inputMultipleDataP},t.ptm("inputMultiple")),[(s(!0),c(B,null,N(t.d_value,function(d,a){return s(),c("li",r({key:"".concat(a,"_").concat(o.getOptionLabel(d)),id:t.$id+"_multiple_option_"+a,class:t.cx("chipItem",{i:a}),role:"option","aria-label":o.getOptionLabel(d),"aria-selected":!0,"aria-setsize":t.d_value.length,"aria-posinset":a+1},{ref_for:!0},t.ptm("chipItem")),[m(t.$slots,"chip",r({class:t.cx("pcChip"),value:d,index:a,removeCallback:function(y){return o.removeOption(y,a)}},{ref_for:!0},t.ptm("pcChip")),function(){return[T(h,{class:k(t.cx("pcChip")),label:o.getOptionLabel(d),removeIcon:t.chipIcon||t.removeTokenIcon,removable:"",unstyled:t.unstyled,onRemove:function(y){return o.removeOption(y,a)},"data-p-focused":l.focusedMultipleOptionIndex===a,pt:t.ptm("pcChip")},{removeicon:x(function(){return[m(t.$slots,t.$slots.chipicon?"chipicon":"removetokenicon",{class:k(t.cx("chipIcon")),index:a,removeCallback:function(y){return o.removeOption(y,a)}})]}),_:2},1032,["class","label","removeIcon","unstyled","onRemove","data-p-focused","pt"])]})],16,je)}),128)),C("li",r({class:t.cx("inputChip"),role:"option"},t.ptm("inputChip")),[C("input",r({ref:"focusInput",id:t.inputId,type:"text",style:t.inputStyle,class:t.inputClass,placeholder:t.placeholder,tabindex:t.disabled?-1:t.tabindex,disabled:t.disabled,autocomplete:"off",role:"combobox","aria-label":t.ariaLabel,"aria-labelledby":t.ariaLabelledby,"aria-haspopup":"listbox","aria-autocomplete":"list","aria-expanded":l.overlayVisible,"aria-controls":t.$id+"_list","aria-activedescendant":l.focused?o.focusedOptionId:void 0,"aria-invalid":t.invalid||void 0,onFocus:e[0]||(e[0]=function(){return o.onFocus&&o.onFocus.apply(o,arguments)}),onBlur:e[1]||(e[1]=function(){return o.onBlur&&o.onBlur.apply(o,arguments)}),onKeydown:e[2]||(e[2]=function(){return o.onKeyDown&&o.onKeyDown.apply(o,arguments)}),onInput:e[3]||(e[3]=function(){return o.onInput&&o.onInput.apply(o,arguments)}),onChange:e[4]||(e[4]=function(){return o.onChange&&o.onChange.apply(o,arguments)})},t.ptm("input")),null,16,Ge)],16)],16,ze)):O("",!0),l.searching||t.loading?m(t.$slots,t.$slots.loader?"loader":"loadingicon",{key:2,class:k(t.cx("loader"))},function(){return[t.loader||t.loadingIcon?(s(),c("i",r({key:0,class:["pi-spin",t.cx("loader"),t.loader,t.loadingIcon],"aria-hidden":"true","data-p-has-dropdown":t.dropdown},t.ptm("loader")),null,16,Ne)):(s(),R(b,r({key:1,class:t.cx("loader"),spin:"","aria-hidden":"true","data-p-has-dropdown":t.dropdown},t.ptm("loader")),null,16,["class","data-p-has-dropdown"]))]}):O("",!0),m(t.$slots,t.$slots.dropdown?"dropdown":"dropdownbutton",{toggleCallback:function(a){return o.onDropdownClick(a)}},function(){return[t.dropdown?(s(),c("button",r({key:0,ref:"dropdownButton",type:"button",class:[t.cx("dropdown"),t.dropdownClass],disabled:t.disabled,"aria-haspopup":"listbox","aria-expanded":l.overlayVisible,"aria-controls":o.panelId,onClick:e[8]||(e[8]=function(){return o.onDropdownClick&&o.onDropdownClick.apply(o,arguments)})},t.ptm("dropdown")),[m(t.$slots,"dropdownicon",{class:k(t.dropdownIcon)},function(){return[(s(),R(ue(t.dropdownIcon?"span":"ChevronDownIcon"),r({class:t.dropdownIcon},t.ptm("dropdownIcon")),null,16,["class"]))]})],16,He)):O("",!0)]}),t.typeahead?(s(),c("span",r({key:3,role:"status","aria-live":"polite",class:"p-hidden-accessible"},t.ptm("hiddenSearchResult"),{"data-p-hidden-accessible":!0}),M(o.searchResultMessageText),17)):O("",!0),T(W,{appendTo:t.appendTo},{default:x(function(){return[T(de,r({name:"p-connected-overlay",onEnter:o.onOverlayEnter,onAfterEnter:o.onOverlayAfterEnter,onLeave:o.onOverlayLeave,onAfterLeave:o.onOverlayAfterLeave},t.ptm("transition")),{default:x(function(){return[l.overlayVisible?(s(),c("div",r({key:0,ref:o.overlayRef,id:o.panelId,class:[t.cx("overlay"),t.panelClass,t.overlayClass],style:q(q({},t.panelStyle),t.overlayStyle),onClick:e[9]||(e[9]=function(){return o.onOverlayClick&&o.onOverlayClick.apply(o,arguments)}),onKeydown:e[10]||(e[10]=function(){return o.onOverlayKeyDown&&o.onOverlayKeyDown.apply(o,arguments)}),"data-p":o.overlayDataP},t.ptm("overlay")),[m(t.$slots,"header",{value:t.d_value,suggestions:o.visibleOptions}),C("div",r({class:t.cx("listContainer"),style:{"max-height":o.virtualScrollerDisabled?t.scrollHeight:""}},t.ptm("listContainer")),[T(A,r({ref:o.virtualScrollerRef},t.virtualScrollerOptions,{style:{height:t.scrollHeight},items:o.visibleOptions,tabindex:-1,disabled:o.virtualScrollerDisabled,pt:t.ptm("virtualScroller")}),pe({content:x(function(d){var a=d.styleClass,K=d.contentRef,y=d.items,g=d.getItemOptions,J=d.contentStyle,D=d.itemSize;return[C("ul",r({ref:function(f){return o.listRef(f,K)},id:t.$id+"_list",class:[t.cx("list"),a],style:J,role:"listbox","aria-label":o.listAriaLabel},t.ptm("list")),[(s(!0),c(B,null,N(y,function(u,f){return s(),c(B,{key:o.getOptionRenderKey(u,o.getOptionIndex(f,g))},[o.isOptionGroup(u)?(s(),c("li",r({key:0,id:t.$id+"_"+o.getOptionIndex(f,g),style:{height:D?D+"px":void 0},class:t.cx("optionGroup"),role:"option"},{ref_for:!0},t.ptm("optionGroup")),[m(t.$slots,"optiongroup",{option:u.optionGroup,index:o.getOptionIndex(f,g)},function(){return[z(M(o.getOptionGroupLabel(u.optionGroup)),1)]})],16,qe)):ce((s(),c("li",r({key:1,id:t.$id+"_"+o.getOptionIndex(f,g),style:{height:D?D+"px":void 0},class:t.cx("option",{option:u,i:f,getItemOptions:g}),role:"option","aria-label":o.getOptionLabel(u),"aria-selected":o.isSelected(u),"aria-disabled":o.isOptionDisabled(u),"aria-setsize":o.ariaSetSize,"aria-posinset":o.getAriaPosInset(o.getOptionIndex(f,g)),onClick:function(P){return o.onOptionSelect(P,u)},onMousemove:function(P){return o.onOptionMouseMove(P,o.getOptionIndex(f,g))},"data-p-selected":o.isSelected(u),"data-p-focused":l.focusedOptionIndex===o.getOptionIndex(f,g),"data-p-disabled":o.isOptionDisabled(u)},{ref_for:!0},o.getPTOptions(u,g,f,"option")),[m(t.$slots,"option",{option:u,index:o.getOptionIndex(f,g)},function(){return[z(M(o.getOptionLabel(u)),1)]})],16,We)),[[Z]])],64)}),128)),t.showEmptyMessage&&(!y||y&&y.length===0)?(s(),c("li",r({key:0,class:t.cx("emptyMessage"),role:"option"},t.ptm("emptyMessage")),[m(t.$slots,"empty",{},function(){return[z(M(o.searchResultMessageText),1)]})],16)):O("",!0)],16,$e)]}),_:2},[t.$slots.loader?{name:"loader",fn:x(function(d){var a=d.options;return[m(t.$slots,"loader",{options:a})]}),key:"0"}:void 0]),1040,["style","items","disabled","pt"])],16),m(t.$slots,"footer",{value:t.d_value,suggestions:o.visibleOptions}),C("span",r({role:"status","aria-live":"polite",class:"p-hidden-accessible"},t.ptm("hiddenSelectedMessage"),{"data-p-hidden-accessible":!0}),M(o.selectedMessageText),17)],16,Ue)):O("",!0)]}),_:3},16,["onEnter","onAfterEnter","onLeave","onAfterLeave"])]}),_:3},8,["appendTo"])],16,Be)}Pe.render=Ze;export{Pe as default};
