import{s as N}from"./BOp_CX-A.js";import{v as T,B as P,bg as m,L as K,c,o as i,a as l,l as h,aa as z,P as a,q as b,Q as y,F as f,s as v,b as O,bh as S}from"./DIVREvd8.js";import{s as x}from"./rzT3uIO0.js";import{s as B}from"./hZJB3ZX-.js";import"./fNKMJjk8.js";var j=T`
    .p-organizationchart-table {
        border-spacing: 0;
        border-collapse: separate;
        margin: 0 auto;
    }

    .p-organizationchart-table > tbody > tr > td {
        text-align: center;
        vertical-align: top;
        padding: 0 dt('organizationchart.gutter');
    }

    .p-organizationchart-node {
        display: inline-block;
        position: relative;
        border: 1px solid dt('organizationchart.node.border.color');
        background: dt('organizationchart.node.background');
        color: dt('organizationchart.node.color');
        padding: dt('organizationchart.node.padding');
        border-radius: dt('organizationchart.node.border.radius');
        transition:
            background dt('organizationchart.transition.duration'),
            border-color dt('organizationchart.transition.duration'),
            color dt('organizationchart.transition.duration'),
            box-shadow dt('organizationchart.transition.duration');
    }

    .p-organizationchart-node:has(.p-organizationchart-node-toggle-button) {
        padding: dt('organizationchart.node.toggleable.padding');
    }

    .p-organizationchart-node.p-organizationchart-node-selectable:not(.p-organizationchart-node-selected):hover {
        background: dt('organizationchart.node.hover.background');
        color: dt('organizationchart.node.hover.color');
    }

    .p-organizationchart-node-selected {
        background: dt('organizationchart.node.selected.background');
        color: dt('organizationchart.node.selected.color');
    }

    .p-organizationchart-node-toggle-button {
        position: absolute;
        inset-block-end: calc(-1 * calc(dt('organizationchart.node.toggle.button.size') / 2));
        margin-inline-start: calc(-1 * calc(dt('organizationchart.node.toggle.button.size') / 2));
        z-index: 2;
        inset-inline-start: 50%;
        user-select: none;
        cursor: pointer;
        width: dt('organizationchart.node.toggle.button.size');
        height: dt('organizationchart.node.toggle.button.size');
        text-decoration: none;
        background: dt('organizationchart.node.toggle.button.background');
        color: dt('organizationchart.node.toggle.button.color');
        border-radius: dt('organizationchart.node.toggle.button.border.radius');
        border: 1px solid dt('organizationchart.node.toggle.button.border.color');
        display: inline-flex;
        justify-content: center;
        align-items: center;
        outline-color: transparent;
        transition:
            background dt('organizationchart.transition.duration'),
            color dt('organizationchart.transition.duration'),
            border-color dt('organizationchart.transition.duration'),
            outline-color dt('organizationchart.transition.duration'),
            box-shadow dt('organizationchart.transition.duration');
    }

    .p-organizationchart-node-toggle-button:hover {
        background: dt('organizationchart.node.toggle.button.hover.background');
        color: dt('organizationchart.node.toggle.button.hover.color');
    }

    .p-organizationchart-node-toggle-button:focus-visible {
        box-shadow: dt('breadcrumb.item.focus.ring.shadow');
        outline: dt('breadcrumb.item.focus.ring.width') dt('breadcrumb.item.focus.ring.style') dt('breadcrumb.item.focus.ring.color');
        outline-offset: dt('breadcrumb.item.focus.ring.offset');
    }

    .p-organizationchart-node-toggle-button-icon {
        position: relative;
        inset-block-start: 1px;
    }

    .p-organizationchart-connector-down {
        margin: 0 auto;
        height: dt('organizationchart.connector.height');
        width: 1px;
        background: dt('organizationchart.connector.color');
    }

    .p-organizationchart-connector-right {
        border-radius: 0;
    }

    .p-organizationchart-connector-left {
        border-radius: 0;
        border-inline-end: 1px solid dt('organizationchart.connector.color');
    }

    .p-organizationchart-connector-top {
        border-block-start: 1px solid dt('organizationchart.connector.color');
    }

    .p-organizationchart-node-selectable {
        cursor: pointer;
    }

    .p-organizationchart-connectors :nth-child(1 of .p-organizationchart-connector-left) {
        border-inline-end: 0 none;
    }

    .p-organizationchart-connectors :nth-last-child(1 of .p-organizationchart-connector-left) {
        border-start-end-radius: dt('organizationchart.connector.border.radius');
    }

    .p-organizationchart-connectors :nth-child(1 of .p-organizationchart-connector-right) {
        border-inline-start: 1px solid dt('organizationchart.connector.color');
        border-start-start-radius: dt('organizationchart.connector.border.radius');
    }
`,D={root:"p-organizationchart p-component",table:"p-organizationchart-table",node:function(t){var o=t.instance;return["p-organizationchart-node",{"p-organizationchart-node-selectable":o.selectable,"p-organizationchart-node-selected":o.selected}]},nodeToggleButton:function(t){var o=t.instance;return["p-organizationchart-node-toggle-button",{"p-disabled":!o.selectable}]},nodeToggleButtonIcon:"p-organizationchart-node-toggle-button-icon",connectors:"p-organizationchart-connectors",connectorDown:"p-organizationchart-connector-down",connectorLeft:function(t){var o=t.index;return["p-organizationchart-connector-left",{"p-organizationchart-connector-top":o!==0}]},connectorRight:function(t){var o=t.props,r=t.index;return["p-organizationchart-connector-right",{"p-organizationchart-connector-top":r!==o.node.children.length-1}]},nodeChildren:"p-organizationchart-node-children"},M=P.extend({name:"organizationchart",style:j,classes:D}),I={name:"BaseOrganizationChart",extends:N,props:{value:{type:null,default:null},selectionKeys:{type:null,default:null},selectionMode:{type:String,default:null},collapsible:{type:Boolean,default:!1},collapsedKeys:{type:null,default:null}},style:M,provide:function(){return{$pcOrganizationChart:this,$parentInstance:this}}},w={name:"OrganizationChartNode",hostName:"OrganizationChart",extends:N,emits:["node-click","node-toggle"],props:{node:{type:null,default:null},templates:{type:null,default:null},collapsible:{type:Boolean,default:!1},collapsedKeys:{type:null,default:null},selectionKeys:{type:null,default:null},selectionMode:{type:String,default:null}},methods:{getPTOptions:function(t){return this.ptm(t,{context:{expanded:this.expanded,selectable:this.selectable,selected:this.selected,toggleable:this.toggleable,active:this.selected}})},getNodeOptions:function(t,o){return this.ptm(o,{context:{lineTop:t}})},onNodeClick:function(t){m(t.target,"data-pc-section","nodetogglebutton")||m(t.target,"data-pc-section","nodetogglebuttonicon")||this.selectionMode&&this.$emit("node-click",this.node)},onChildNodeClick:function(t){this.$emit("node-click",t)},toggleNode:function(){this.$emit("node-toggle",this.node)},onChildNodeToggle:function(t){this.$emit("node-toggle",t)},onKeydown:function(t){(t.code==="Enter"||t.code==="NumpadEnter"||t.code==="Space")&&(this.toggleNode(),t.preventDefault())}},computed:{leaf:function(){return this.node.leaf===!1?!1:!(this.node.children&&this.node.children.length)},colspan:function(){return this.node.children&&this.node.children.length?this.node.children.length*2:null},childStyle:function(){return{visibility:!this.leaf&&this.expanded?"inherit":"hidden"}},expanded:function(){return this.collapsedKeys[this.node.key]===void 0},selectable:function(){return this.selectionMode&&this.node.selectable!==!1},selected:function(){return this.selectable&&this.selectionKeys&&this.selectionKeys[this.node.key]===!0},toggleable:function(){return this.collapsible&&this.node.collapsible!==!1&&!this.leaf}},components:{ChevronDownIcon:x,ChevronUpIcon:B}},E=["colspan"],L=["colspan"],R=["colspan"];function V(e,t,o,r,s,n){var p=K("OrganizationChartNode",!0);return i(),c("table",a({class:e.cx("table")},e.ptm("table")),[l("tbody",z(S(e.ptm("body"))),[o.node?(i(),c("tr",z(a({key:0},e.ptm("row"))),[l("td",a({colspan:n.colspan},e.ptm("cell")),[l("div",a({class:[e.cx("node"),o.node.styleClass],onClick:t[2]||(t[2]=function(){return n.onNodeClick&&n.onNodeClick.apply(n,arguments)})},n.getPTOptions("node")),[(i(),b(y(o.templates[o.node.type]||o.templates.default),{node:o.node},null,8,["node"])),n.toggleable?(i(),c("a",a({key:0,tabindex:"0",class:e.cx("nodeToggleButton"),onClick:t[0]||(t[0]=function(){return n.toggleNode&&n.toggleNode.apply(n,arguments)}),onKeydown:t[1]||(t[1]=function(){return n.onKeydown&&n.onKeydown.apply(n,arguments)})},n.getPTOptions("nodeToggleButton")),[o.templates.toggleicon||o.templates.togglericon?(i(),b(y(o.templates.toggleicon||o.templates.togglericon),a({key:0,expanded:n.expanded,class:e.cx("nodeToggleButtonIcon")},n.getPTOptions("nodeToggleButtonIcon")),null,16,["expanded","class"])):(i(),b(y(n.expanded?"ChevronDownIcon":"ChevronUpIcon"),a({key:1,class:e.cx("nodeToggleButtonIcon")},n.getPTOptions("nodeToggleButtonIcon")),null,16,["class"]))],16)):h("",!0)],16)],16,E)],16)):h("",!0),l("tr",a({style:n.childStyle,class:e.cx("connectors")},e.ptm("connectors")),[l("td",a({colspan:n.colspan},e.ptm("lineCell")),[l("div",a({class:e.cx("connectorDown")},e.ptm("connectorDown")),null,16)],16,L)],16),l("tr",a({style:n.childStyle,class:e.cx("connectors")},e.ptm("connectors")),[o.node.children&&o.node.children.length===1?(i(),c("td",a({key:0,colspan:n.colspan},e.ptm("lineCell")),[l("div",a({class:e.cx("connectorDown")},e.ptm("connectorDown")),null,16)],16,R)):h("",!0),o.node.children&&o.node.children.length>1?(i(!0),c(f,{key:1},v(o.node.children,function(g,u){return i(),c(f,{key:g.key},[l("td",a({class:e.cx("connectorLeft",{index:u})},{ref_for:!0},n.getNodeOptions(u!==0,"connectorLeft"))," ",16),l("td",a({class:e.cx("connectorRight",{index:u})},{ref_for:!0},n.getNodeOptions(u!==o.node.children.length-1,"connectorRight"))," ",16)],64)}),128)):h("",!0)],16),l("tr",a({style:n.childStyle,class:e.cx("nodeChildren")},e.ptm("nodeChildren")),[(i(!0),c(f,null,v(o.node.children,function(g){return i(),c("td",a({key:g.key,colspan:"2"},{ref_for:!0},e.ptm("nodeCell")),[O(p,{node:g,templates:o.templates,collapsedKeys:o.collapsedKeys,onNodeToggle:n.onChildNodeToggle,collapsible:o.collapsible,selectionMode:o.selectionMode,selectionKeys:o.selectionKeys,onNodeClick:n.onChildNodeClick,pt:e.pt,unstyled:e.unstyled},null,8,["node","templates","collapsedKeys","onNodeToggle","collapsible","selectionMode","selectionKeys","onNodeClick","pt","unstyled"])],16)}),128))],16)],16)],16)}w.render=V;function d(e){"@babel/helpers - typeof";return d=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(t){return typeof t}:function(t){return t&&typeof Symbol=="function"&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},d(e)}function k(e,t){var o=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter(function(s){return Object.getOwnPropertyDescriptor(e,s).enumerable})),o.push.apply(o,r)}return o}function C(e){for(var t=1;t<arguments.length;t++){var o=arguments[t]!=null?arguments[t]:{};t%2?k(Object(o),!0).forEach(function(r){q(e,r,o[r])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(o)):k(Object(o)).forEach(function(r){Object.defineProperty(e,r,Object.getOwnPropertyDescriptor(o,r))})}return e}function q(e,t,o){return(t=A(t))in e?Object.defineProperty(e,t,{value:o,enumerable:!0,configurable:!0,writable:!0}):e[t]=o,e}function A(e){var t=F(e,"string");return d(t)=="symbol"?t:t+""}function F(e,t){if(d(e)!="object"||!e)return e;var o=e[Symbol.toPrimitive];if(o!==void 0){var r=o.call(e,t);if(d(r)!="object")return r;throw new TypeError("@@toPrimitive must return a primitive value.")}return(t==="string"?String:Number)(e)}var U={name:"OrganizationChart",extends:I,inheritAttrs:!1,emits:["node-unselect","node-select","update:selectionKeys","node-expand","node-collapse","update:collapsedKeys"],data:function(){return{d_collapsedKeys:this.collapsedKeys||{}}},watch:{collapsedKeys:function(t){this.d_collapsedKeys=t}},methods:{onNodeClick:function(t){var o=t.key;if(this.selectionMode){var r=this.selectionKeys?C({},this.selectionKeys):{};r[o]?(delete r[o],this.$emit("node-unselect",t)):(this.selectionMode==="single"&&(r={}),r[o]=!0,this.$emit("node-select",t)),this.$emit("update:selectionKeys",r)}},onNodeToggle:function(t){var o=t.key;this.d_collapsedKeys[o]?(delete this.d_collapsedKeys[o],this.$emit("node-expand",t)):(this.d_collapsedKeys[o]=!0,this.$emit("node-collapse",t)),this.d_collapsedKeys=C({},this.d_collapsedKeys),this.$emit("update:collapsedKeys",this.d_collapsedKeys)}},components:{OrganizationChartNode:w}};function Q(e,t,o,r,s,n){var p=K("OrganizationChartNode");return i(),c("div",a({class:e.cx("root")},e.ptmi("root")),[O(p,{node:e.value,templates:e.$slots,onNodeToggle:n.onNodeToggle,collapsedKeys:s.d_collapsedKeys,collapsible:e.collapsible,onNodeClick:n.onNodeClick,selectionMode:e.selectionMode,selectionKeys:e.selectionKeys,pt:e.pt,unstyled:e.unstyled},null,8,["node","templates","onNodeToggle","collapsedKeys","collapsible","onNodeClick","selectionMode","selectionKeys","pt","unstyled"])],16)}U.render=Q;export{U as default};
