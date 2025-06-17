import{c as D}from"./Be1fzYNM.js";import{v as Z,B as _,aJ as $,K as ee,a2 as N,z as F,a0 as L,L as y,M as te,c as h,o as s,a as I,l as S,S as ne,q as f,P as u,F as v,O as g,Q as b,w as A,m as oe,d as re,t as ie,s as W,N as T,b as M}from"./DWUyzV_3.js";import{s as le}from"./kftMLlWQ.js";import{s as q}from"./By04JIrE.js";import ae from"./BrVI1-wI.js";import de from"./Dsksf0sy.js";import se from"./lezfXGT4.js";import{s as J}from"./B-kZlFfU.js";import{s as ce}from"./1XHi02cs.js";import{s as ue}from"./BftmRrQc.js";import{s as fe}from"./QlyqZk-1.js";import{s as he}from"./BBPg_8E7.js";import pe from"./epWB24ap.js";import{R as ye}from"./B3MqGaNe.js";import"./A4PA4AXe.js";import"./Djaqmldt.js";import"./C_hGxOta.js";import"./Ds2a9wz8.js";import"./DmfvTtO6.js";var ge=Z`
    .p-tree {
        background: dt('tree.background');
        color: dt('tree.color');
        padding: dt('tree.padding');
    }

    .p-tree-root-children,
    .p-tree-node-children {
        display: flex;
        list-style-type: none;
        flex-direction: column;
        margin: 0;
        gap: dt('tree.gap');
    }

    .p-tree-root-children {
        padding: 0;
        padding-block-start: dt('tree.gap');
    }

    .p-tree-node-children {
        padding: 0;
        padding-block-start: dt('tree.gap');
        padding-inline-start: dt('tree.indent');
    }

    .p-tree-node {
        padding: 0;
        outline: 0 none;
    }

    .p-tree-node-content {
        border-radius: dt('tree.node.border.radius');
        padding: dt('tree.node.padding');
        display: flex;
        align-items: center;
        outline-color: transparent;
        color: dt('tree.node.color');
        gap: dt('tree.node.gap');
        transition:
            background dt('tree.transition.duration'),
            color dt('tree.transition.duration'),
            outline-color dt('tree.transition.duration'),
            box-shadow dt('tree.transition.duration');
    }

    .p-tree-node:focus-visible > .p-tree-node-content {
        box-shadow: dt('tree.node.focus.ring.shadow');
        outline: dt('tree.node.focus.ring.width') dt('tree.node.focus.ring.style') dt('tree.node.focus.ring.color');
        outline-offset: dt('tree.node.focus.ring.offset');
    }

    .p-tree-node-content.p-tree-node-selectable:not(.p-tree-node-selected):hover {
        background: dt('tree.node.hover.background');
        color: dt('tree.node.hover.color');
    }

    .p-tree-node-content.p-tree-node-selectable:not(.p-tree-node-selected):hover .p-tree-node-icon {
        color: dt('tree.node.icon.hover.color');
    }

    .p-tree-node-content.p-tree-node-selected {
        background: dt('tree.node.selected.background');
        color: dt('tree.node.selected.color');
    }

    .p-tree-node-content.p-tree-node-selected .p-tree-node-toggle-button {
        color: inherit;
    }

    .p-tree-node-toggle-button {
        cursor: pointer;
        user-select: none;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        overflow: hidden;
        position: relative;
        flex-shrink: 0;
        width: dt('tree.node.toggle.button.size');
        height: dt('tree.node.toggle.button.size');
        color: dt('tree.node.toggle.button.color');
        border: 0 none;
        background: transparent;
        border-radius: dt('tree.node.toggle.button.border.radius');
        transition:
            background dt('tree.transition.duration'),
            color dt('tree.transition.duration'),
            border-color dt('tree.transition.duration'),
            outline-color dt('tree.transition.duration'),
            box-shadow dt('tree.transition.duration');
        outline-color: transparent;
        padding: 0;
    }

    .p-tree-node-toggle-button:enabled:hover {
        background: dt('tree.node.toggle.button.hover.background');
        color: dt('tree.node.toggle.button.hover.color');
    }

    .p-tree-node-content.p-tree-node-selected .p-tree-node-toggle-button:hover {
        background: dt('tree.node.toggle.button.selected.hover.background');
        color: dt('tree.node.toggle.button.selected.hover.color');
    }

    .p-tree-root {
        overflow: auto;
    }

    .p-tree-node-selectable {
        cursor: pointer;
        user-select: none;
    }

    .p-tree-node-leaf > .p-tree-node-content .p-tree-node-toggle-button {
        visibility: hidden;
    }

    .p-tree-node-icon {
        color: dt('tree.node.icon.color');
        transition: color dt('tree.transition.duration');
    }

    .p-tree-node-content.p-tree-node-selected .p-tree-node-icon {
        color: dt('tree.node.icon.selected.color');
    }

    .p-tree-filter {
        margin: dt('tree.filter.margin');
    }

    .p-tree-filter-input {
        width: 100%;
    }

    .p-tree-loading {
        position: relative;
        height: 100%;
    }

    .p-tree-loading-icon {
        font-size: dt('tree.loading.icon.size');
        width: dt('tree.loading.icon.size');
        height: dt('tree.loading.icon.size');
    }

    .p-tree .p-tree-mask {
        position: absolute;
        z-index: 1;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .p-tree-flex-scrollable {
        display: flex;
        flex: 1;
        height: 100%;
        flex-direction: column;
    }

    .p-tree-flex-scrollable .p-tree-root {
        flex: 1;
    }
`,be={root:function(t){var n=t.props;return["p-tree p-component",{"p-tree-selectable":n.selectionMode!=null,"p-tree-loading":n.loading,"p-tree-flex-scrollable":n.scrollHeight==="flex"}]},mask:"p-tree-mask p-overlay-mask",loadingIcon:"p-tree-loading-icon",pcFilterContainer:"p-tree-filter",pcFilterInput:"p-tree-filter-input",wrapper:"p-tree-root",rootChildren:"p-tree-root-children",node:function(t){var n=t.instance;return["p-tree-node",{"p-tree-node-leaf":n.leaf}]},nodeContent:function(t){var n=t.instance;return["p-tree-node-content",n.node.styleClass,{"p-tree-node-selectable":n.selectable,"p-tree-node-selected":n.checkboxMode&&n.$parentInstance.highlightOnSelect?n.checked:n.selected}]},nodeToggleButton:"p-tree-node-toggle-button",nodeToggleIcon:"p-tree-node-toggle-icon",nodeCheckbox:"p-tree-node-checkbox",nodeIcon:"p-tree-node-icon",nodeLabel:"p-tree-node-label",nodeChildren:"p-tree-node-children"},me=_.extend({name:"tree",style:ge,classes:be}),ke={name:"BaseTree",extends:J,props:{value:{type:null,default:null},expandedKeys:{type:null,default:null},selectionKeys:{type:null,default:null},selectionMode:{type:String,default:null},metaKeySelection:{type:Boolean,default:!1},loading:{type:Boolean,default:!1},loadingIcon:{type:String,default:void 0},loadingMode:{type:String,default:"mask"},filter:{type:Boolean,default:!1},filterBy:{type:[String,Function],default:"label"},filterMode:{type:String,default:"lenient"},filterPlaceholder:{type:String,default:null},filterLocale:{type:String,default:void 0},highlightOnSelect:{type:Boolean,default:!1},scrollHeight:{type:String,default:null},level:{type:Number,default:0},ariaLabelledby:{type:String,default:null},ariaLabel:{type:String,default:null}},style:me,provide:function(){return{$pcTree:this,$parentInstance:this}}};function C(e){"@babel/helpers - typeof";return C=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(t){return typeof t}:function(t){return t&&typeof Symbol=="function"&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},C(e)}function V(e,t){var n=typeof Symbol<"u"&&e[Symbol.iterator]||e["@@iterator"];if(!n){if(Array.isArray(e)||(n=Q(e))||t){n&&(e=n);var o=0,i=function(){};return{s:i,n:function(){return o>=e.length?{done:!0}:{done:!1,value:e[o++]}},e:function(d){throw d},f:i}}throw new TypeError(`Invalid attempt to iterate non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}var r,l=!0,c=!1;return{s:function(){n=n.call(e)},n:function(){var d=n.next();return l=d.done,d},e:function(d){c=!0,r=d},f:function(){try{l||n.return==null||n.return()}finally{if(c)throw r}}}}function B(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);t&&(o=o.filter(function(i){return Object.getOwnPropertyDescriptor(e,i).enumerable})),n.push.apply(n,o)}return n}function R(e){for(var t=1;t<arguments.length;t++){var n=arguments[t]!=null?arguments[t]:{};t%2?B(Object(n),!0).forEach(function(o){ve(e,o,n[o])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):B(Object(n)).forEach(function(o){Object.defineProperty(e,o,Object.getOwnPropertyDescriptor(n,o))})}return e}function ve(e,t,n){return(t=Se(t))in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function Se(e){var t=Ce(e,"string");return C(t)=="symbol"?t:t+""}function Ce(e,t){if(C(e)!="object"||!e)return e;var n=e[Symbol.toPrimitive];if(n!==void 0){var o=n.call(e,t);if(C(o)!="object")return o;throw new TypeError("@@toPrimitive must return a primitive value.")}return(t==="string"?String:Number)(e)}function k(e){return Ke(e)||we(e)||Q(e)||xe()}function xe(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function Q(e,t){if(e){if(typeof e=="string")return j(e,t);var n={}.toString.call(e).slice(8,-1);return n==="Object"&&e.constructor&&(n=e.constructor.name),n==="Map"||n==="Set"?Array.from(e):n==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?j(e,t):void 0}}function we(e){if(typeof Symbol<"u"&&e[Symbol.iterator]!=null||e["@@iterator"]!=null)return Array.from(e)}function Ke(e){if(Array.isArray(e))return j(e)}function j(e,t){(t==null||t>e.length)&&(t=e.length);for(var n=0,o=Array(t);n<t;n++)o[n]=e[n];return o}var G={name:"TreeNode",hostName:"Tree",extends:J,emits:["node-toggle","node-click","checkbox-change"],props:{node:{type:null,default:null},expandedKeys:{type:null,default:null},loadingMode:{type:String,default:"mask"},selectionKeys:{type:null,default:null},selectionMode:{type:String,default:null},templates:{type:null,default:null},level:{type:Number,default:null},index:null},nodeTouched:!1,toggleClicked:!1,mounted:function(){this.setAllNodesTabIndexes()},methods:{toggle:function(){this.$emit("node-toggle",this.node),this.toggleClicked=!0},label:function(t){return typeof t.label=="function"?t.label():t.label},onChildNodeToggle:function(t){this.$emit("node-toggle",t)},getPTOptions:function(t){return this.ptm(t,{context:{node:this.node,index:this.index,expanded:this.expanded,selected:this.selected,checked:this.checked,partialChecked:this.partialChecked,leaf:this.leaf}})},onClick:function(t){if(this.toggleClicked||N(t.target,'[data-pc-section="nodetogglebutton"]')||N(t.target.parentElement,'[data-pc-section="nodetogglebutton"]')){this.toggleClicked=!1;return}this.isCheckboxSelectionMode()?this.node.selectable!=!1&&this.toggleCheckbox():this.$emit("node-click",{originalEvent:t,nodeTouched:this.nodeTouched,node:this.node}),this.nodeTouched=!1},onChildNodeClick:function(t){this.$emit("node-click",t)},onTouchEnd:function(){this.nodeTouched=!0},onKeyDown:function(t){if(this.isSameNode(t))switch(t.code){case"Tab":this.onTabKey(t);break;case"ArrowDown":this.onArrowDown(t);break;case"ArrowUp":this.onArrowUp(t);break;case"ArrowRight":this.onArrowRight(t);break;case"ArrowLeft":this.onArrowLeft(t);break;case"Enter":case"NumpadEnter":case"Space":this.onEnterKey(t);break}},onArrowDown:function(t){var n=t.target.getAttribute("data-pc-section")==="nodetogglebutton"?t.target.closest('[role="treeitem"]'):t.target,o=n.children[1];if(o)this.focusRowChange(n,o.children[0]);else if(n.nextElementSibling)this.focusRowChange(n,n.nextElementSibling);else{var i=this.findNextSiblingOfAncestor(n);i&&this.focusRowChange(n,i)}t.preventDefault()},onArrowUp:function(t){var n=t.target;if(n.previousElementSibling)this.focusRowChange(n,n.previousElementSibling,this.findLastVisibleDescendant(n.previousElementSibling));else{var o=this.getParentNodeElement(n);o&&this.focusRowChange(n,o)}t.preventDefault()},onArrowRight:function(t){var n=this;this.leaf||this.expanded||(t.currentTarget.tabIndex=-1,this.$emit("node-toggle",this.node),this.$nextTick(function(){n.onArrowDown(t)}))},onArrowLeft:function(t){var n=F(t.currentTarget,'[data-pc-section="nodetogglebutton"]');if(this.level===0&&!this.expanded)return!1;if(this.expanded&&!this.leaf)return n.click(),!1;var o=this.findBeforeClickableNode(t.currentTarget);o&&this.focusRowChange(t.currentTarget,o)},onEnterKey:function(t){this.setTabIndexForSelectionMode(t,this.nodeTouched),this.onClick(t),t.preventDefault()},onTabKey:function(){this.setAllNodesTabIndexes()},setAllNodesTabIndexes:function(){var t=L(this.$refs.currentNode.closest('[data-pc-section="rootchildren"]'),'[role="treeitem"]'),n=k(t).some(function(i){return i.getAttribute("aria-selected")==="true"||i.getAttribute("aria-checked")==="true"});if(k(t).forEach(function(i){i.tabIndex=-1}),n){var o=k(t).filter(function(i){return i.getAttribute("aria-selected")==="true"||i.getAttribute("aria-checked")==="true"});o[0].tabIndex=0;return}k(t)[0].tabIndex=0},setTabIndexForSelectionMode:function(t,n){if(this.selectionMode!==null){var o=k(L(this.$refs.currentNode.parentElement,'[role="treeitem"]'));t.currentTarget.tabIndex=n===!1?-1:0,o.every(function(i){return i.tabIndex===-1})&&(o[0].tabIndex=0)}},focusRowChange:function(t,n,o){t.tabIndex="-1",n.tabIndex="0",this.focusNode(o||n)},findBeforeClickableNode:function(t){var n=t.closest("ul").closest("li");if(n){var o=F(n,"button");return o&&o.style.visibility!=="hidden"?n:this.findBeforeClickableNode(t.previousElementSibling)}return null},toggleCheckbox:function(){var t=this.selectionKeys?R({},this.selectionKeys):{},n=!this.checked;this.propagateDown(this.node,n,t),this.$emit("checkbox-change",{node:this.node,check:n,selectionKeys:t})},propagateDown:function(t,n,o){if(n&&t.selectable!=!1?o[t.key]={checked:!0,partialChecked:!1}:delete o[t.key],t.children&&t.children.length){var i=V(t.children),r;try{for(i.s();!(r=i.n()).done;){var l=r.value;this.propagateDown(l,n,o)}}catch(c){i.e(c)}finally{i.f()}}},propagateUp:function(t){var n=t.check,o=R({},t.selectionKeys),i=0,r=!1,l=V(this.node.children),c;try{for(l.s();!(c=l.n()).done;){var a=c.value;o[a.key]&&o[a.key].checked?i++:o[a.key]&&o[a.key].partialChecked&&(r=!0)}}catch(d){l.e(d)}finally{l.f()}n&&i===this.node.children.length?o[this.node.key]={checked:!0,partialChecked:!1}:(n||delete o[this.node.key],r||i>0&&i!==this.node.children.length?o[this.node.key]={checked:!1,partialChecked:!0}:delete o[this.node.key]),this.$emit("checkbox-change",{node:t.node,check:t.check,selectionKeys:o})},onChildCheckboxChange:function(t){this.$emit("checkbox-change",t)},findNextSiblingOfAncestor:function(t){var n=this.getParentNodeElement(t);return n?n.nextElementSibling?n.nextElementSibling:this.findNextSiblingOfAncestor(n):null},findLastVisibleDescendant:function(t){var n=t.children[1];if(n){var o=n.children[n.children.length-1];return this.findLastVisibleDescendant(o)}else return t},getParentNodeElement:function(t){var n=t.parentElement.parentElement;return N(n,"role")==="treeitem"?n:null},focusNode:function(t){t.focus()},isCheckboxSelectionMode:function(){return this.selectionMode==="checkbox"},isSameNode:function(t){return t.currentTarget&&(t.currentTarget.isSameNode(t.target)||t.currentTarget.isSameNode(t.target.closest('[role="treeitem"]')))}},computed:{hasChildren:function(){return this.node.children&&this.node.children.length>0},expanded:function(){return this.expandedKeys&&this.expandedKeys[this.node.key]===!0},leaf:function(){return this.node.leaf===!1?!1:!(this.node.children&&this.node.children.length)},selectable:function(){return this.node.selectable===!1?!1:this.selectionMode!=null},selected:function(){return this.selectionMode&&this.selectionKeys?this.selectionKeys[this.node.key]===!0:!1},checkboxMode:function(){return this.selectionMode==="checkbox"&&this.node.selectable!==!1},checked:function(){return this.selectionKeys?this.selectionKeys[this.node.key]&&this.selectionKeys[this.node.key].checked:!1},partialChecked:function(){return this.selectionKeys?this.selectionKeys[this.node.key]&&this.selectionKeys[this.node.key].partialChecked:!1},ariaChecked:function(){return this.selectionMode==="single"||this.selectionMode==="multiple"?this.selected:void 0},ariaSelected:function(){return this.checkboxMode?this.checked:void 0}},components:{Checkbox:pe,ChevronDownIcon:ue,ChevronRightIcon:fe,CheckIcon:ce,MinusIcon:he,SpinnerIcon:q},directives:{ripple:ye}},Te=["aria-label","aria-selected","aria-expanded","aria-setsize","aria-posinset","aria-level","aria-checked","tabindex"],Ie=["data-p-selected","data-p-selectable"],Oe=["data-p-leaf"];function Ne(e,t,n,o,i,r){var l=y("SpinnerIcon"),c=y("Checkbox"),a=y("TreeNode",!0),d=te("ripple");return s(),h("li",u({ref:"currentNode",class:e.cx("node"),role:"treeitem","aria-label":r.label(n.node),"aria-selected":r.ariaSelected,"aria-expanded":r.expanded,"aria-setsize":n.node.children?n.node.children.length:0,"aria-posinset":n.index+1,"aria-level":n.level,"aria-checked":r.ariaChecked,tabindex:n.index===0?0:-1,onKeydown:t[4]||(t[4]=function(){return r.onKeyDown&&r.onKeyDown.apply(r,arguments)})},r.getPTOptions("node")),[I("div",u({class:e.cx("nodeContent"),onClick:t[2]||(t[2]=function(){return r.onClick&&r.onClick.apply(r,arguments)}),onTouchend:t[3]||(t[3]=function(){return r.onTouchEnd&&r.onTouchEnd.apply(r,arguments)}),style:n.node.style},r.getPTOptions("nodeContent"),{"data-p-selected":r.checkboxMode?r.checked:r.selected,"data-p-selectable":r.selectable}),[ne((s(),h("button",u({type:"button",class:e.cx("nodeToggleButton"),onClick:t[0]||(t[0]=function(){return r.toggle&&r.toggle.apply(r,arguments)}),tabindex:"-1","data-p-leaf":r.leaf},r.getPTOptions("nodeToggleButton")),[n.node.loading&&n.loadingMode==="icon"?(s(),h(v,{key:0},[n.templates.nodetoggleicon||n.templates.nodetogglericon?(s(),f(b(n.templates.nodetoggleicon||n.templates.nodetogglericon),{key:0,node:n.node,expanded:r.expanded,class:g(e.cx("nodeToggleIcon"))},null,8,["node","expanded","class"])):(s(),f(l,u({key:1,spin:"",class:e.cx("nodeToggleIcon")},r.getPTOptions("nodeToggleIcon")),null,16,["class"]))],64)):(s(),h(v,{key:1},[n.templates.nodetoggleicon||n.templates.togglericon?(s(),f(b(n.templates.nodetoggleicon||n.templates.togglericon),{key:0,node:n.node,expanded:r.expanded,class:g(e.cx("nodeToggleIcon"))},null,8,["node","expanded","class"])):r.expanded?(s(),f(b(n.node.expandedIcon?"span":"ChevronDownIcon"),u({key:1,class:e.cx("nodeToggleIcon")},r.getPTOptions("nodeToggleIcon")),null,16,["class"])):(s(),f(b(n.node.collapsedIcon?"span":"ChevronRightIcon"),u({key:2,class:e.cx("nodeToggleIcon")},r.getPTOptions("nodeToggleIcon")),null,16,["class"]))],64))],16,Oe)),[[d]]),r.checkboxMode?(s(),f(c,{key:0,defaultValue:r.checked,binary:!0,indeterminate:r.partialChecked,class:g(e.cx("nodeCheckbox")),tabindex:-1,unstyled:e.unstyled,pt:r.getPTOptions("pcNodeCheckbox"),"data-p-partialchecked":r.partialChecked},{icon:A(function(p){return[n.templates.checkboxicon?(s(),f(b(n.templates.checkboxicon),{key:0,checked:p.checked,partialChecked:r.partialChecked,class:g(p.class)},null,8,["checked","partialChecked","class"])):S("",!0)]}),_:1},8,["defaultValue","indeterminate","class","unstyled","pt","data-p-partialchecked"])):S("",!0),n.templates.nodeicon?(s(),f(b(n.templates.nodeicon),u({key:1,node:n.node,class:[e.cx("nodeIcon")]},r.getPTOptions("nodeIcon")),null,16,["node","class"])):(s(),h("span",u({key:2,class:[e.cx("nodeIcon"),n.node.icon]},r.getPTOptions("nodeIcon")),null,16)),I("span",u({class:e.cx("nodeLabel")},r.getPTOptions("nodeLabel"),{onKeydown:t[1]||(t[1]=oe(function(){},["stop"]))}),[n.templates[n.node.type]||n.templates.default?(s(),f(b(n.templates[n.node.type]||n.templates.default),{key:0,node:n.node,expanded:r.expanded,selected:r.checkboxMode?r.checked:r.selected},null,8,["node","expanded","selected"])):(s(),h(v,{key:1},[re(ie(r.label(n.node)),1)],64))],16)],16,Ie),r.hasChildren&&r.expanded?(s(),h("ul",u({key:0,class:e.cx("nodeChildren"),role:"group"},e.ptm("nodeChildren")),[(s(!0),h(v,null,W(n.node.children,function(p){return s(),f(a,{key:p.key,node:p,templates:n.templates,level:n.level+1,loadingMode:n.loadingMode,expandedKeys:n.expandedKeys,onNodeToggle:r.onChildNodeToggle,onNodeClick:r.onChildNodeClick,selectionMode:n.selectionMode,selectionKeys:n.selectionKeys,onCheckboxChange:r.propagateUp,unstyled:e.unstyled,pt:e.pt},null,8,["node","templates","level","loadingMode","expandedKeys","onNodeToggle","onNodeClick","selectionMode","selectionKeys","onCheckboxChange","unstyled","pt"])}),128))],16)):S("",!0)],16,Te)}G.render=Ne;function x(e){"@babel/helpers - typeof";return x=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(t){return typeof t}:function(t){return t&&typeof Symbol=="function"&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},x(e)}function P(e,t){var n=typeof Symbol<"u"&&e[Symbol.iterator]||e["@@iterator"];if(!n){if(Array.isArray(e)||(n=X(e))||t){n&&(e=n);var o=0,i=function(){};return{s:i,n:function(){return o>=e.length?{done:!0}:{done:!1,value:e[o++]}},e:function(d){throw d},f:i}}throw new TypeError(`Invalid attempt to iterate non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}var r,l=!0,c=!1;return{s:function(){n=n.call(e)},n:function(){var d=n.next();return l=d.done,d},e:function(d){c=!0,r=d},f:function(){try{l||n.return==null||n.return()}finally{if(c)throw r}}}}function Me(e){return je(e)||Ae(e)||X(e)||Pe()}function Pe(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function X(e,t){if(e){if(typeof e=="string")return E(e,t);var n={}.toString.call(e).slice(8,-1);return n==="Object"&&e.constructor&&(n=e.constructor.name),n==="Map"||n==="Set"?Array.from(e):n==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?E(e,t):void 0}}function Ae(e){if(typeof Symbol<"u"&&e[Symbol.iterator]!=null||e["@@iterator"]!=null)return Array.from(e)}function je(e){if(Array.isArray(e))return E(e)}function E(e,t){(t==null||t>e.length)&&(t=e.length);for(var n=0,o=Array(t);n<t;n++)o[n]=e[n];return o}function z(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);t&&(o=o.filter(function(i){return Object.getOwnPropertyDescriptor(e,i).enumerable})),n.push.apply(n,o)}return n}function m(e){for(var t=1;t<arguments.length;t++){var n=arguments[t]!=null?arguments[t]:{};t%2?z(Object(n),!0).forEach(function(o){Ee(e,o,n[o])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):z(Object(n)).forEach(function(o){Object.defineProperty(e,o,Object.getOwnPropertyDescriptor(n,o))})}return e}function Ee(e,t,n){return(t=De(t))in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function De(e){var t=Fe(e,"string");return x(t)=="symbol"?t:t+""}function Fe(e,t){if(x(e)!="object"||!e)return e;var n=e[Symbol.toPrimitive];if(n!==void 0){var o=n.call(e,t);if(x(o)!="object")return o;throw new TypeError("@@toPrimitive must return a primitive value.")}return(t==="string"?String:Number)(e)}var Le={name:"Tree",extends:ke,inheritAttrs:!1,emits:["node-expand","node-collapse","update:expandedKeys","update:selectionKeys","node-select","node-unselect","filter"],data:function(){return{d_expandedKeys:this.expandedKeys||{},filterValue:null}},watch:{expandedKeys:function(t){this.d_expandedKeys=t}},methods:{onNodeToggle:function(t){var n=t.key;this.d_expandedKeys[n]?(delete this.d_expandedKeys[n],this.$emit("node-collapse",t)):(this.d_expandedKeys[n]=!0,this.$emit("node-expand",t)),this.d_expandedKeys=m({},this.d_expandedKeys),this.$emit("update:expandedKeys",this.d_expandedKeys)},onNodeClick:function(t){if(this.selectionMode!=null&&t.node.selectable!==!1){var n=t.nodeTouched?!1:this.metaKeySelection,o=n?this.handleSelectionWithMetaKey(t):this.handleSelectionWithoutMetaKey(t);this.$emit("update:selectionKeys",o)}},onCheckboxChange:function(t){this.$emit("update:selectionKeys",t.selectionKeys),t.check?this.$emit("node-select",t.node):this.$emit("node-unselect",t.node)},handleSelectionWithMetaKey:function(t){var n=t.originalEvent,o=t.node,i=n.metaKey||n.ctrlKey,r=this.isNodeSelected(o),l;return r&&i?(this.isSingleSelectionMode()?l={}:(l=m({},this.selectionKeys),delete l[o.key]),this.$emit("node-unselect",o)):(this.isSingleSelectionMode()?l={}:this.isMultipleSelectionMode()&&(l=i?this.selectionKeys?m({},this.selectionKeys):{}:{}),l[o.key]=!0,this.$emit("node-select",o)),l},handleSelectionWithoutMetaKey:function(t){var n=t.node,o=this.isNodeSelected(n),i;return this.isSingleSelectionMode()?o?(i={},this.$emit("node-unselect",n)):(i={},i[n.key]=!0,this.$emit("node-select",n)):o?(i=m({},this.selectionKeys),delete i[n.key],this.$emit("node-unselect",n)):(i=this.selectionKeys?m({},this.selectionKeys):{},i[n.key]=!0,this.$emit("node-select",n)),i},isSingleSelectionMode:function(){return this.selectionMode==="single"},isMultipleSelectionMode:function(){return this.selectionMode==="multiple"},isNodeSelected:function(t){return this.selectionMode&&this.selectionKeys?this.selectionKeys[t.key]===!0:!1},isChecked:function(t){return this.selectionKeys?this.selectionKeys[t.key]&&this.selectionKeys[t.key].checked:!1},isNodeLeaf:function(t){return t.leaf===!1?!1:!(t.children&&t.children.length)},onFilterKeyup:function(t){(t.code==="Enter"||t.code==="NumpadEnter")&&t.preventDefault(),this.$emit("filter",{originalEvent:t,value:t.target.value})},findFilteredNodes:function(t,n){if(t){var o=!1;if(t.children){var i=Me(t.children);t.children=[];var r=P(i),l;try{for(r.s();!(l=r.n()).done;){var c=l.value,a=m({},c);this.isFilterMatched(a,n)&&(o=!0,t.children.push(a))}}catch(d){r.e(d)}finally{r.f()}}if(o)return!0}},isFilterMatched:function(t,n){var o=n.searchFields,i=n.filterText,r=n.strict,l=!1,c=P(o),a;try{for(c.s();!(a=c.n()).done;){var d=a.value,p=String(ee(t,d)).toLocaleLowerCase(this.filterLocale);p.indexOf(i)>-1&&(l=!0)}}catch(O){c.e(O)}finally{c.f()}return(!l||r&&!this.isNodeLeaf(t))&&(l=this.findFilteredNodes(t,{searchFields:o,filterText:i,strict:r})||l),l}},computed:{filteredValue:function(){var t=[],n=$(this.filterBy)?[this.filterBy]:this.filterBy.split(","),o=this.filterValue.trim().toLocaleLowerCase(this.filterLocale),i=this.filterMode==="strict",r=P(this.value),l;try{for(r.s();!(l=r.n()).done;){var c=l.value,a=m({},c),d={searchFields:n,filterText:o,strict:i};(i&&(this.findFilteredNodes(a,d)||this.isFilterMatched(a,d))||!i&&(this.isFilterMatched(a,d)||this.findFilteredNodes(a,d)))&&t.push(a)}}catch(p){r.e(p)}finally{r.f()}return t},valueToRender:function(){return this.filterValue&&this.filterValue.trim().length>0?this.filteredValue:this.value},containerDataP:function(){return D({loading:this.loading,scrollable:this.scrollHeight==="flex"})},wrapperDataP:function(){return D({scrollable:this.scrollHeight==="flex"})}},components:{TreeNode:G,InputText:se,InputIcon:de,IconField:ae,SearchIcon:le,SpinnerIcon:q}};function w(e){"@babel/helpers - typeof";return w=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(t){return typeof t}:function(t){return t&&typeof Symbol=="function"&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},w(e)}function H(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);t&&(o=o.filter(function(i){return Object.getOwnPropertyDescriptor(e,i).enumerable})),n.push.apply(n,o)}return n}function U(e){for(var t=1;t<arguments.length;t++){var n=arguments[t]!=null?arguments[t]:{};t%2?H(Object(n),!0).forEach(function(o){Ve(e,o,n[o])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):H(Object(n)).forEach(function(o){Object.defineProperty(e,o,Object.getOwnPropertyDescriptor(n,o))})}return e}function Ve(e,t,n){return(t=Be(t))in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function Be(e){var t=Re(e,"string");return w(t)=="symbol"?t:t+""}function Re(e,t){if(w(e)!="object"||!e)return e;var n=e[Symbol.toPrimitive];if(n!==void 0){var o=n.call(e,t);if(w(o)!="object")return o;throw new TypeError("@@toPrimitive must return a primitive value.")}return(t==="string"?String:Number)(e)}var ze=["data-p"],He=["data-p"],Ue=["aria-labelledby","aria-label"];function We(e,t,n,o,i,r){var l=y("SpinnerIcon"),c=y("InputText"),a=y("SearchIcon"),d=y("InputIcon"),p=y("IconField"),O=y("TreeNode");return s(),h("div",u({class:e.cx("root"),"data-p":r.containerDataP},e.ptmi("root")),[e.loading&&e.loadingMode==="mask"?(s(),h("div",u({key:0,class:e.cx("mask")},e.ptm("mask")),[T(e.$slots,"loadingicon",{class:g(e.cx("loadingIcon"))},function(){return[e.loadingIcon?(s(),h("i",u({key:0,class:[e.cx("loadingIcon"),"pi-spin",e.loadingIcon]},e.ptm("loadingIcon")),null,16)):(s(),f(l,u({key:1,spin:"",class:e.cx("loadingIcon")},e.ptm("loadingIcon")),null,16,["class"]))]})],16)):S("",!0),e.filter?(s(),f(p,{key:1,unstyled:e.unstyled,pt:U(U({},e.ptm("pcFilter")),e.ptm("pcFilterContainer")),class:g(e.cx("pcFilterContainer"))},{default:A(function(){return[M(c,{modelValue:i.filterValue,"onUpdate:modelValue":t[0]||(t[0]=function(K){return i.filterValue=K}),autocomplete:"off",class:g(e.cx("pcFilterInput")),placeholder:e.filterPlaceholder,unstyled:e.unstyled,onKeyup:r.onFilterKeyup,pt:e.ptm("pcFilterInput")},null,8,["modelValue","class","placeholder","unstyled","onKeyup","pt"]),M(d,{unstyled:e.unstyled,pt:e.ptm("pcFilterIconContainer")},{default:A(function(){return[T(e.$slots,e.$slots.filtericon?"filtericon":"searchicon",{class:g(e.cx("filterIcon"))},function(){return[M(a,u({class:e.cx("filterIcon")},e.ptm("filterIcon")),null,16,["class"])]})]}),_:3},8,["unstyled","pt"])]}),_:3},8,["unstyled","pt","class"])):S("",!0),I("div",u({class:e.cx("wrapper"),style:{maxHeight:e.scrollHeight},"data-p":r.wrapperDataP},e.ptm("wrapper")),[T(e.$slots,"header",{value:e.value,expandedKeys:e.expandedKeys,selectionKeys:e.selectionKeys}),I("ul",u({class:e.cx("rootChildren"),role:"tree","aria-labelledby":e.ariaLabelledby,"aria-label":e.ariaLabel},e.ptm("rootChildren")),[(s(!0),h(v,null,W(r.valueToRender,function(K,Y){return s(),f(O,{key:K.key,node:K,templates:e.$slots,level:e.level+1,index:Y,expandedKeys:i.d_expandedKeys,onNodeToggle:r.onNodeToggle,onNodeClick:r.onNodeClick,selectionMode:e.selectionMode,selectionKeys:e.selectionKeys,onCheckboxChange:r.onCheckboxChange,loadingMode:e.loadingMode,unstyled:e.unstyled,pt:e.pt},null,8,["node","templates","level","index","expandedKeys","onNodeToggle","onNodeClick","selectionMode","selectionKeys","onCheckboxChange","loadingMode","unstyled","pt"])}),128))],16,Ue),T(e.$slots,"footer",{value:e.value,expandedKeys:e.expandedKeys,selectionKeys:e.selectionKeys})],16,He)],16,ze)}Le.render=We;export{Le as default};
