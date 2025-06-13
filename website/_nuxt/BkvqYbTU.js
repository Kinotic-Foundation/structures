import{v as B,B as D,x as I,J as H,z as x,D as R,E as U,G as W,H as Z,$ as N,I as q,A as m,a0 as G,a8 as J,a9 as Q,L as E,c,o as a,a as f,N as u,l as v,b as k,P as s,F as g,d as T,t as O,s as X,O as y,q as $,Q as P,w as b,T as Y,m as _,R as ee}from"./DZN9uhBn.js";import{Z as K}from"./DGEOgZpe.js";import{C as te}from"./CcH2w7Jn.js";import{s as ne}from"./WGN9oHPH.js";import{s as re}from"./CvhXVTn7.js";import le from"./Mq22MW4V.js";import{O as ie}from"./CZighkSe.js";import{s as oe}from"./BAYyFvuT.js";import{R as se}from"./CP7dxGoz.js";import ae from"./BLHC7PKk.js";import{s as de}from"./CBpRjKCn.js";import"./C1vqh6FR.js";import"./1noTU6vz.js";import"./Be1fzYNM.js";import"./BdbeFC2P.js";import"./DX3-MJaN.js";import"./DmfvTtO6.js";import"./mLf4kUGy.js";import"./CF46Zw-l.js";import"./ax5BTckd.js";import"./DPjProQX.js";import"./DQeSQPbM.js";import"./DT7vfX-F.js";import"./BNRZS_mC.js";import"./B8bQ3o7D.js";import"./B5-xG9Gp.js";import"./CtDvjYuq.js";var ce=B`
    .p-treeselect {
        display: inline-flex;
        cursor: pointer;
        position: relative;
        user-select: none;
        background: dt('treeselect.background');
        border: 1px solid dt('treeselect.border.color');
        transition:
            background dt('treeselect.transition.duration'),
            color dt('treeselect.transition.duration'),
            border-color dt('treeselect.transition.duration'),
            outline-color dt('treeselect.transition.duration'),
            box-shadow dt('treeselect.transition.duration');
        border-radius: dt('treeselect.border.radius');
        outline-color: transparent;
        box-shadow: dt('treeselect.shadow');
    }

    .p-treeselect:not(.p-disabled):hover {
        border-color: dt('treeselect.hover.border.color');
    }

    .p-treeselect:not(.p-disabled).p-focus {
        border-color: dt('treeselect.focus.border.color');
        box-shadow: dt('treeselect.focus.ring.shadow');
        outline: dt('treeselect.focus.ring.width') dt('treeselect.focus.ring.style') dt('treeselect.focus.ring.color');
        outline-offset: dt('treeselect.focus.ring.offset');
    }

    .p-treeselect.p-variant-filled {
        background: dt('treeselect.filled.background');
    }

    .p-treeselect.p-variant-filled:not(.p-disabled):hover {
        background: dt('treeselect.filled.hover.background');
    }

    .p-treeselect.p-variant-filled.p-focus {
        background: dt('treeselect.filled.focus.background');
    }

    .p-treeselect.p-invalid {
        border-color: dt('treeselect.invalid.border.color');
    }

    .p-treeselect.p-disabled {
        opacity: 1;
        background: dt('treeselect.disabled.background');
    }

    .p-treeselect-clear-icon {
        position: absolute;
        top: 50%;
        margin-top: -0.5rem;
        color: dt('treeselect.clear.icon.color');
        inset-inline-end: dt('treeselect.dropdown.width');
    }

    .p-treeselect-dropdown {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-shrink: 0;
        background: transparent;
        color: dt('treeselect.dropdown.color');
        width: dt('treeselect.dropdown.width');
        border-start-end-radius: dt('border.radius.md');
        border-end-end-radius: dt('border.radius.md');
    }

    .p-treeselect-label-container {
        overflow: hidden;
        flex: 1 1 auto;
        cursor: pointer;
    }

    .p-treeselect-label {
        display: flex;
        align-items: center;
        gap: calc(dt('treeselect.padding.y') / 2);
        white-space: nowrap;
        cursor: pointer;
        overflow: hidden;
        text-overflow: ellipsis;
        padding: dt('treeselect.padding.y') dt('treeselect.padding.x');
        color: dt('treeselect.color');
    }

    .p-treeselect-label.p-placeholder {
        color: dt('treeselect.placeholder.color');
    }

    .p-treeselect.p-invalid .p-treeselect-label.p-placeholder {
        color: dt('treeselect.invalid.placeholder.color');
    }

    .p-treeselect.p-disabled .p-treeselect-label {
        color: dt('treeselect.disabled.color');
    }

    .p-treeselect-label-empty {
        overflow: hidden;
        visibility: hidden;
    }

    .p-treeselect .p-treeselect-overlay {
        min-width: 100%;
    }

    .p-treeselect-overlay {
        position: absolute;
        top: 0;
        left: 0;
        background: dt('treeselect.overlay.background');
        color: dt('treeselect.overlay.color');
        border: 1px solid dt('treeselect.overlay.border.color');
        border-radius: dt('treeselect.overlay.border.radius');
        box-shadow: dt('treeselect.overlay.shadow');
        overflow: hidden;
    }

    .p-treeselect-tree-container {
        overflow: auto;
    }

    .p-treeselect-empty-message {
        padding: dt('treeselect.empty.message.padding');
        background: transparent;
    }

    .p-treeselect-fluid {
        display: flex;
    }

    .p-treeselect-overlay .p-tree {
        padding: dt('treeselect.tree.padding');
    }

    .p-treeselect-overlay .p-tree-loading {
        min-height: 3rem;
    }

    .p-treeselect-label .p-chip {
        padding-block-start: calc(dt('treeselect.padding.y') / 2);
        padding-block-end: calc(dt('treeselect.padding.y') / 2);
        border-radius: dt('treeselect.chip.border.radius');
    }

    .p-treeselect-label:has(.p-chip) {
        padding: calc(dt('treeselect.padding.y') / 2) calc(dt('treeselect.padding.x') / 2);
    }

    .p-treeselect-sm .p-treeselect-label {
        font-size: dt('treeselect.sm.font.size');
        padding-block: dt('treeselect.sm.padding.y');
        padding-inline: dt('treeselect.sm.padding.x');
    }

    .p-treeselect-sm .p-treeselect-dropdown .p-icon {
        font-size: dt('treeselect.sm.font.size');
        width: dt('treeselect.sm.font.size');
        height: dt('treeselect.sm.font.size');
    }

    .p-treeselect-lg .p-treeselect-label {
        font-size: dt('treeselect.lg.font.size');
        padding-block: dt('treeselect.lg.padding.y');
        padding-inline: dt('treeselect.lg.padding.x');
    }

    .p-treeselect-lg .p-treeselect-dropdown .p-icon {
        font-size: dt('treeselect.lg.font.size');
        width: dt('treeselect.lg.font.size');
        height: dt('treeselect.lg.font.size');
    }
`,ue={root:function(t){var n=t.props;return{position:n.appendTo==="self"?"relative":void 0}}},pe={root:function(t){var n=t.instance,r=t.props;return["p-treeselect p-component p-inputwrapper",{"p-treeselect-display-chip":r.display==="chip","p-disabled":r.disabled,"p-invalid":n.$invalid,"p-focus":n.focused,"p-variant-filled":n.$variant==="filled","p-inputwrapper-filled":n.$filled,"p-inputwrapper-focus":n.focused||n.overlayVisible,"p-treeselect-open":n.overlayVisible,"p-treeselect-fluid":n.$fluid,"p-treeselect-sm p-inputfield-sm":r.size==="small","p-treeselect-lg p-inputfield-lg":r.size==="large"}]},labelContainer:"p-treeselect-label-container",label:function(t){var n=t.instance,r=t.props;return["p-treeselect-label",{"p-placeholder":n.label===r.placeholder,"p-treeselect-label-empty":!r.placeholder&&n.emptyValue}]},clearIcon:"p-treeselect-clear-icon",chip:"p-treeselect-chip-item",pcChip:"p-treeselect-chip",dropdown:"p-treeselect-dropdown",dropdownIcon:"p-treeselect-dropdown-icon",panel:"p-treeselect-overlay p-component",treeContainer:"p-treeselect-tree-container",emptyMessage:"p-treeselect-empty-message"},fe=D.extend({name:"treeselect",style:ce,classes:pe,inlineStyles:ue}),he={name:"BaseTreeSelect",extends:de,props:{options:Array,scrollHeight:{type:String,default:"20rem"},placeholder:{type:String,default:null},tabindex:{type:Number,default:null},selectionMode:{type:String,default:"single"},selectedItemsLabel:{type:String,default:null},maxSelectedLabels:{type:Number,default:null},appendTo:{type:[String,Object],default:"body"},emptyMessage:{type:String,default:null},display:{type:String,default:"comma"},metaKeySelection:{type:Boolean,default:!1},loading:{type:Boolean,default:!1},loadingIcon:{type:String,default:void 0},loadingMode:{type:String,default:"mask"},showClear:{type:Boolean,default:!1},clearIcon:{type:String,default:void 0},filter:{type:Boolean,default:!1},filterBy:{type:[String,Function],default:"label"},filterMode:{type:String,default:"lenient"},filterPlaceholder:{type:String,default:null},filterLocale:{type:String,default:void 0},inputId:{type:String,default:null},inputClass:{type:[String,Object],default:null},inputStyle:{type:Object,default:null},inputProps:{type:null,default:null},panelClass:{type:[String,Object],default:null},panelProps:{type:null,default:null},ariaLabelledby:{type:String,default:null},ariaLabel:{type:String,default:null},expandedKeys:{type:null,default:null}},style:fe,provide:function(){return{$pcTreeSelect:this,$parentInstance:this}}};function w(e){"@babel/helpers - typeof";return w=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(t){return typeof t}:function(t){return t&&typeof Symbol=="function"&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},w(e)}function j(e,t){var n=typeof Symbol<"u"&&e[Symbol.iterator]||e["@@iterator"];if(!n){if(Array.isArray(e)||(n=M(e))||t){n&&(e=n);var r=0,o=function(){};return{s:o,n:function(){return r>=e.length?{done:!0}:{done:!1,value:e[r++]}},e:function(i){throw i},f:o}}throw new TypeError(`Invalid attempt to iterate non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}var l,h=!0,p=!1;return{s:function(){n=n.call(e)},n:function(){var i=n.next();return h=i.done,i},e:function(i){p=!0,l=i},f:function(){try{h||n.return==null||n.return()}finally{if(p)throw l}}}}function V(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter(function(o){return Object.getOwnPropertyDescriptor(e,o).enumerable})),n.push.apply(n,r)}return n}function A(e){for(var t=1;t<arguments.length;t++){var n=arguments[t]!=null?arguments[t]:{};t%2?V(Object(n),!0).forEach(function(r){ye(e,r,n[r])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):V(Object(n)).forEach(function(r){Object.defineProperty(e,r,Object.getOwnPropertyDescriptor(n,r))})}return e}function ye(e,t,n){return(t=be(t))in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function be(e){var t=me(e,"string");return w(t)=="symbol"?t:t+""}function me(e,t){if(w(e)!="object"||!e)return e;var n=e[Symbol.toPrimitive];if(n!==void 0){var r=n.call(e,t);if(w(r)!="object")return r;throw new TypeError("@@toPrimitive must return a primitive value.")}return(t==="string"?String:Number)(e)}function ve(e){return Se(e)||we(e)||M(e)||ge()}function ge(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function M(e,t){if(e){if(typeof e=="string")return F(e,t);var n={}.toString.call(e).slice(8,-1);return n==="Object"&&e.constructor&&(n=e.constructor.name),n==="Map"||n==="Set"?Array.from(e):n==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?F(e,t):void 0}}function we(e){if(typeof Symbol<"u"&&e[Symbol.iterator]!=null||e["@@iterator"]!=null)return Array.from(e)}function Se(e){if(Array.isArray(e))return F(e)}function F(e,t){(t==null||t>e.length)&&(t=e.length);for(var n=0,r=Array(t);n<t;n++)r[n]=e[n];return r}var ke={name:"TreeSelect",extends:he,inheritAttrs:!1,emits:["before-show","before-hide","change","show","hide","node-select","node-unselect","node-expand","node-collapse","focus","blur","update:expandedKeys"],inject:{$pcFluid:{default:null}},data:function(){return{focused:!1,overlayVisible:!1,d_expandedKeys:this.expandedKeys||{}}},watch:{modelValue:{handler:function(){this.selfChange||this.updateTreeState(),this.selfChange=!1},immediate:!0},options:function(){this.updateTreeState()},expandedKeys:function(t){this.d_expandedKeys=t}},outsideClickListener:null,resizeListener:null,scrollHandler:null,overlay:null,selfChange:!1,selfClick:!1,beforeUnmount:function(){this.unbindOutsideClickListener(),this.unbindResizeListener(),this.scrollHandler&&(this.scrollHandler.destroy(),this.scrollHandler=null),this.overlay&&(K.clear(this.overlay),this.overlay=null)},mounted:function(){this.updateTreeState()},methods:{show:function(){this.$emit("before-show"),this.overlayVisible=!0},hide:function(){this.$emit("before-hide"),this.overlayVisible=!1,this.$refs.focusInput.focus()},onFocus:function(t){this.focused=!0,this.$emit("focus",t)},onBlur:function(t){var n,r;this.focused=!1,this.$emit("blur",t),(n=(r=this.formField).onBlur)===null||n===void 0||n.call(r)},onClick:function(t){this.disabled||t.target.tagName==="INPUT"||t.target.getAttribute("data-pc-section")==="clearicon"||t.target.closest('[data-pc-section="clearicon"]')||(!this.overlay||!this.overlay.contains(t.target))&&(this.overlayVisible?this.hide():this.show(),m(this.$refs.focusInput))},onClearClick:function(){this.onSelectionChange(null)},onSelectionChange:function(t){this.selfChange=!0,this.writeValue(t),this.$emit("change",t)},onNodeSelect:function(t){this.$emit("node-select",t),this.selectionMode==="single"&&this.hide()},onNodeUnselect:function(t){this.$emit("node-unselect",t)},onNodeToggle:function(t){this.d_expandedKeys=t,this.$emit("update:expandedKeys",this.d_expandedKeys)},getSelectedItemsLabel:function(){var t=/{(.*?)}/,n=this.selectedItemsLabel||this.$primevue.config.locale.selectionMessage;return t.test(n)?n.replace(n.match(t)[0],Object.keys(this.d_value).length+""):n},onFirstHiddenFocus:function(t){var n=t.relatedTarget===this.$refs.focusInput?Q(this.overlay,':not([data-p-hidden-focusable="true"])'):this.$refs.focusInput;m(n)},onLastHiddenFocus:function(t){var n=t.relatedTarget===this.$refs.focusInput?J(this.overlay,':not([data-p-hidden-focusable="true"])'):this.$refs.focusInput;m(n)},onKeyDown:function(t){switch(t.code){case"ArrowDown":this.onArrowDownKey(t);break;case"Space":case"Enter":case"NumpadEnter":this.onEnterKey(t);break;case"Escape":this.onEscapeKey(t);break;case"Tab":this.onTabKey(t);break}},onArrowDownKey:function(t){var n=this;this.overlayVisible||(this.show(),this.$nextTick(function(){var r=G(n.$refs.tree.$el,'[data-pc-section="treeitem"]'),o=ve(r).find(function(l){return l.getAttribute("tabindex")==="0"});m(o)}),t.preventDefault())},onEnterKey:function(t){this.overlayVisible?this.hide():this.onArrowDownKey(t),t.preventDefault()},onEscapeKey:function(t){this.overlayVisible&&(this.hide(),t.preventDefault())},onTabKey:function(t){var n=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1;n||this.overlayVisible&&this.hasFocusableElements()&&(m(this.$refs.firstHiddenFocusableElementOnOverlay),t.preventDefault())},hasFocusableElements:function(){return N(this.overlay,':not([data-p-hidden-focusable="true"])').length>0},onOverlayEnter:function(t){K.set("overlay",t,this.$primevue.config.zIndex.overlay),q(t,{position:"absolute",top:"0"}),this.alignOverlay(),this.focus(),this.$attrSelector&&t.setAttribute(this.$attrSelector,"")},onOverlayAfterEnter:function(){this.bindOutsideClickListener(),this.bindScrollListener(),this.bindResizeListener(),this.scrollValueInView(),this.$emit("show")},onOverlayLeave:function(){this.unbindOutsideClickListener(),this.unbindScrollListener(),this.unbindResizeListener(),this.$emit("hide"),this.overlay=null},onOverlayAfterLeave:function(t){K.clear(t)},focus:function(){var t=N(this.overlay);t&&t.length>0&&t[0].focus()},alignOverlay:function(){this.appendTo==="self"?U(this.overlay,this.$el):(this.overlay.style.minWidth=W(this.$el)+"px",Z(this.overlay,this.$el))},bindOutsideClickListener:function(){var t=this;this.outsideClickListener||(this.outsideClickListener=function(n){t.overlayVisible&&!t.selfClick&&t.isOutsideClicked(n)&&t.hide(),t.selfClick=!1},document.addEventListener("click",this.outsideClickListener,!0))},unbindOutsideClickListener:function(){this.outsideClickListener&&(document.removeEventListener("click",this.outsideClickListener,!0),this.outsideClickListener=null)},bindScrollListener:function(){var t=this;this.scrollHandler||(this.scrollHandler=new te(this.$refs.container,function(){t.overlayVisible&&t.hide()})),this.scrollHandler.bindScrollListener()},unbindScrollListener:function(){this.scrollHandler&&this.scrollHandler.unbindScrollListener()},bindResizeListener:function(){var t=this;this.resizeListener||(this.resizeListener=function(){t.overlayVisible&&!R()&&t.hide()},window.addEventListener("resize",this.resizeListener))},unbindResizeListener:function(){this.resizeListener&&(window.removeEventListener("resize",this.resizeListener),this.resizeListener=null)},isOutsideClicked:function(t){return!(this.$el.isSameNode(t.target)||this.$el.contains(t.target)||this.overlay&&this.overlay.contains(t.target))},overlayRef:function(t){this.overlay=t},onOverlayClick:function(t){ie.emit("overlay-click",{originalEvent:t,target:this.$el}),this.selfClick=!0},onOverlayKeydown:function(t){t.code==="Escape"&&this.hide()},fillNodeMap:function(t,n){var r,o=this;n[t.key]=t,(r=t.children)!==null&&r!==void 0&&r.length&&t.children.forEach(function(l){return o.fillNodeMap(l,n)})},isSelected:function(t,n){return this.selectionMode==="checkbox"?n[t.key]&&n[t.key].checked:n[t.key]},updateTreeState:function(){var t=A({},this.d_value);t&&this.options&&this.updateTreeBranchState(null,null,t)},updateTreeBranchState:function(t,n,r){if(t){if(this.isSelected(t,r)&&(this.expandPath(n),delete r[t.key]),Object.keys(r).length&&t.children){var o=j(t.children),l;try{for(o.s();!(l=o.n()).done;){var h=l.value;n.push(t.key),this.updateTreeBranchState(h,n,r)}}catch(L){o.e(L)}finally{o.f()}}}else{var p=j(this.options),d;try{for(p.s();!(d=p.n()).done;){var i=d.value;this.updateTreeBranchState(i,[],r)}}catch(L){p.e(L)}finally{p.f()}}},expandPath:function(t){if(t.length>0){var n=j(t),r;try{for(n.s();!(r=n.n()).done;){var o=r.value;this.d_expandedKeys[o]=!0}}catch(l){n.e(l)}finally{n.f()}this.d_expandedKeys=A({},this.d_expandedKeys),this.$emit("update:expandedKeys",this.d_expandedKeys)}},scrollValueInView:function(){if(this.overlay){var t=x(this.overlay,'[data-p-selected="true"]');t&&t.scrollIntoView({block:"nearest",inline:"start"})}}},computed:{nodeMap:function(){var t,n=this,r={};return(t=this.options)===null||t===void 0||t.forEach(function(o){return n.fillNodeMap(o,r)}),r},selectedNodes:function(){var t=this,n=[];return this.d_value&&this.options&&Object.keys(this.d_value).forEach(function(r){var o=t.nodeMap[r];t.isSelected(o,t.d_value)&&n.push(o)}),n},label:function(){var t=this.selectedNodes,n;return t.length?I(this.maxSelectedLabels)&&t.length>this.maxSelectedLabels?n=this.getSelectedItemsLabel():n=t.map(function(r){return r.label}).join(", "):n=this.placeholder,n},chipSelectedItems:function(){return I(this.maxSelectedLabels)&&this.d_value&&Object.keys(this.d_value).length>this.maxSelectedLabels},emptyMessageText:function(){return this.emptyMessage||this.$primevue.config.locale.emptyMessage},emptyValue:function(){return!this.$filled},emptyOptions:function(){return!this.options||this.options.length===0},listId:function(){return this.$id+"_list"},hasFluid:function(){return H(this.fluid)?!!this.$pcFluid:this.fluid},isClearIconVisible:function(){return this.showClear&&this.d_value!=null&&I(this.options)}},components:{TSTree:ae,Chip:le,Portal:oe,ChevronDownIcon:ne,TimesIcon:re},directives:{ripple:se}};function S(e){"@babel/helpers - typeof";return S=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(t){return typeof t}:function(t){return t&&typeof Symbol=="function"&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},S(e)}function z(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter(function(o){return Object.getOwnPropertyDescriptor(e,o).enumerable})),n.push.apply(n,r)}return n}function C(e){for(var t=1;t<arguments.length;t++){var n=arguments[t]!=null?arguments[t]:{};t%2?z(Object(n),!0).forEach(function(r){Oe(e,r,n[r])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):z(Object(n)).forEach(function(r){Object.defineProperty(e,r,Object.getOwnPropertyDescriptor(n,r))})}return e}function Oe(e,t,n){return(t=Ce(t))in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function Ce(e){var t=Le(e,"string");return S(t)=="symbol"?t:t+""}function Le(e,t){if(S(e)!="object"||!e)return e;var n=e[Symbol.toPrimitive];if(n!==void 0){var r=n.call(e,t);if(S(r)!="object")return r;throw new TypeError("@@toPrimitive must return a primitive value.")}return(t==="string"?String:Number)(e)}var Ie=["id","disabled","tabindex","aria-labelledby","aria-label","aria-expanded","aria-controls"],Ee={key:0},Te=["aria-expanded"];function Ke(e,t,n,r,o,l){var h=E("Chip"),p=E("TSTree"),d=E("Portal");return a(),c("div",s({ref:"container",class:e.cx("root"),style:e.sx("root"),onClick:t[10]||(t[10]=function(){return l.onClick&&l.onClick.apply(l,arguments)})},e.ptmi("root")),[f("div",s({class:"p-hidden-accessible"},e.ptm("hiddenInputContainer"),{"data-p-hidden-accessible":!0}),[f("input",s({ref:"focusInput",id:e.inputId,type:"text",role:"combobox",class:e.inputClass,style:e.inputStyle,readonly:"",disabled:e.disabled,tabindex:e.disabled?-1:e.tabindex,"aria-labelledby":e.ariaLabelledby,"aria-label":e.ariaLabel,"aria-haspopup":"tree","aria-expanded":o.overlayVisible,"aria-controls":l.listId,onFocus:t[0]||(t[0]=function(i){return l.onFocus(i)}),onBlur:t[1]||(t[1]=function(i){return l.onBlur(i)}),onKeydown:t[2]||(t[2]=function(i){return l.onKeyDown(i)})},C(C({},e.inputProps),e.ptm("hiddenInput"))),null,16,Ie)],16),f("div",s({class:e.cx("labelContainer")},e.ptm("labelContainer")),[f("div",s({class:e.cx("label")},e.ptm("label")),[u(e.$slots,"value",{value:l.selectedNodes,placeholder:e.placeholder},function(){return[e.display==="comma"?(a(),c(g,{key:0},[T(O(l.label||"empty"),1)],64)):e.display==="chip"?(a(),c(g,{key:1},[l.chipSelectedItems?(a(),c("span",Ee,O(l.label),1)):(a(),c(g,{key:1},[(a(!0),c(g,null,X(l.selectedNodes,function(i){return a(),c("div",s({key:i.key,class:e.cx("chipItem")},{ref_for:!0},e.ptm("chipItem")),[k(h,{class:y(e.cx("pcChip")),label:i.label,unstyled:e.unstyled,pt:e.ptm("pcChip")},null,8,["class","label","unstyled","pt"])],16)}),128)),l.emptyValue?(a(),c(g,{key:0},[T(O(e.placeholder||"empty"),1)],64)):v("",!0)],64))],64)):v("",!0)]})],16)],16),l.isClearIconVisible?u(e.$slots,"clearicon",{key:0,class:y(e.cx("clearIcon")),clearCallback:l.onClearClick},function(){return[(a(),$(P(e.clearIcon?"i":"TimesIcon"),s({ref:"clearIcon",class:[e.cx("clearIcon"),e.clearIcon],onClick:l.onClearClick},e.ptm("clearIcon"),{"data-pc-section":"clearicon"}),null,16,["class","onClick"]))]}):v("",!0),f("div",s({class:e.cx("dropdown"),role:"button","aria-haspopup":"tree","aria-expanded":o.overlayVisible},e.ptm("dropdown")),[u(e.$slots,e.$slots.dropdownicon?"dropdownicon":"triggericon",{class:y(e.cx("dropdownIcon"))},function(){return[(a(),$(P("ChevronDownIcon"),s({class:e.cx("dropdownIcon")},e.ptm("dropdownIcon")),null,16,["class"]))]})],16,Te),k(d,{appendTo:e.appendTo},{default:b(function(){return[k(Y,s({name:"p-connected-overlay",onEnter:l.onOverlayEnter,onAfterEnter:l.onOverlayAfterEnter,onLeave:l.onOverlayLeave,onAfterLeave:l.onOverlayAfterLeave},e.ptm("transition")),{default:b(function(){return[o.overlayVisible?(a(),c("div",s({key:0,ref:l.overlayRef,onClick:t[8]||(t[8]=function(){return l.onOverlayClick&&l.onOverlayClick.apply(l,arguments)}),class:[e.cx("panel"),e.panelClass],onKeydown:t[9]||(t[9]=function(){return l.onOverlayKeydown&&l.onOverlayKeydown.apply(l,arguments)})},C(C({},e.panelProps),e.ptm("panel"))),[f("span",s({ref:"firstHiddenFocusableElementOnOverlay",role:"presentation",class:"p-hidden-accessible p-hidden-focusable",tabindex:0,onFocus:t[3]||(t[3]=function(){return l.onFirstHiddenFocus&&l.onFirstHiddenFocus.apply(l,arguments)})},e.ptm("hiddenFirstFocusableEl"),{"data-p-hidden-accessible":!0,"data-p-hidden-focusable":!0}),null,16),u(e.$slots,"header",{value:e.d_value,options:e.options}),f("div",s({class:e.cx("treeContainer"),style:{"max-height":e.scrollHeight}},e.ptm("treeContainer")),[k(p,{ref:"tree",id:l.listId,value:e.options,selectionMode:e.selectionMode,loading:e.loading,loadingIcon:e.loadingIcon,loadingMode:e.loadingMode,filter:e.filter,filterBy:e.filterBy,filterMode:e.filterMode,filterPlaceholder:e.filterPlaceholder,filterLocale:e.filterLocale,"onUpdate:selectionKeys":l.onSelectionChange,selectionKeys:e.d_value,expandedKeys:o.d_expandedKeys,"onUpdate:expandedKeys":l.onNodeToggle,metaKeySelection:e.metaKeySelection,onNodeExpand:t[4]||(t[4]=function(i){return e.$emit("node-expand",i)}),onNodeCollapse:t[5]||(t[5]=function(i){return e.$emit("node-collapse",i)}),onNodeSelect:l.onNodeSelect,onNodeUnselect:l.onNodeUnselect,onClick:t[6]||(t[6]=_(function(){},["stop"])),level:0,unstyled:e.unstyled,pt:e.ptm("pcTree")},ee({_:2},[e.$slots.option?{name:"default",fn:b(function(i){return[u(e.$slots,"option",{node:i.node,expanded:i.expanded,selected:i.selected})]}),key:"0"}:void 0,e.$slots.itemtoggleicon?{name:"toggleicon",fn:b(function(i){return[u(e.$slots,"itemtoggleicon",{node:i.node,expanded:i.expanded,class:y(i.class)})]}),key:"1"}:e.$slots.itemtogglericon?{name:"togglericon",fn:b(function(i){return[u(e.$slots,"itemtogglericon",{node:i.node,expanded:i.expanded,class:y(i.class)})]}),key:"2"}:void 0,e.$slots.itemcheckboxicon?{name:"checkboxicon",fn:b(function(i){return[u(e.$slots,"itemcheckboxicon",{checked:i.checked,partialChecked:i.partialChecked,class:y(i.class)})]}),key:"3"}:void 0]),1032,["id","value","selectionMode","loading","loadingIcon","loadingMode","filter","filterBy","filterMode","filterPlaceholder","filterLocale","onUpdate:selectionKeys","selectionKeys","expandedKeys","onUpdate:expandedKeys","metaKeySelection","onNodeSelect","onNodeUnselect","unstyled","pt"]),l.emptyOptions&&!e.loading?(a(),c("div",s({key:0,class:e.cx("emptyMessage")},e.ptm("emptyMessage")),[u(e.$slots,"empty",{},function(){return[T(O(l.emptyMessageText),1)]})],16)):v("",!0)],16),u(e.$slots,"footer",{value:e.d_value,options:e.options}),f("span",s({ref:"lastHiddenFocusableElementOnOverlay",role:"presentation",class:"p-hidden-accessible p-hidden-focusable",tabindex:0,onFocus:t[7]||(t[7]=function(){return l.onLastHiddenFocus&&l.onLastHiddenFocus.apply(l,arguments)})},e.ptm("hiddenLastFocusableEl"),{"data-p-hidden-accessible":!0,"data-p-hidden-focusable":!0}),null,16)],16)):v("",!0)]}),_:3},16,["onEnter","onAfterEnter","onLeave","onAfterLeave"])]}),_:3},8,["appendTo"])],16)}ke.render=Ke;export{ke as default};
