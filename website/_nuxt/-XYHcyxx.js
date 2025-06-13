import{c as P}from"./Be1fzYNM.js";import{v as b,B as E,aE as x,aW as L,ay as M,ax as T,G as c,az as d,x as w,c as f,o as h,F as v,s as A,q as I,l as R,Q as G,P as m,a as _}from"./DZN9uhBn.js";import{g as z}from"./CcH2w7Jn.js";import{s as k}from"./1noTU6vz.js";var K=b`
    .p-splitter {
        display: flex;
        flex-wrap: nowrap;
        border: 1px solid dt('splitter.border.color');
        background: dt('splitter.background');
        border-radius: dt('border.radius.md');
        color: dt('splitter.color');
    }

    .p-splitter-vertical {
        flex-direction: column;
    }

    .p-splitter-gutter {
        flex-grow: 0;
        flex-shrink: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 1;
        background: dt('splitter.gutter.background');
    }

    .p-splitter-gutter-handle {
        border-radius: dt('splitter.handle.border.radius');
        background: dt('splitter.handle.background');
        transition:
            outline-color dt('splitter.transition.duration'),
            box-shadow dt('splitter.transition.duration');
        outline-color: transparent;
    }

    .p-splitter-gutter-handle:focus-visible {
        box-shadow: dt('splitter.handle.focus.ring.shadow');
        outline: dt('splitter.handle.focus.ring.width') dt('splitter.handle.focus.ring.style') dt('splitter.handle.focus.ring.color');
        outline-offset: dt('splitter.handle.focus.ring.offset');
    }

    .p-splitter-horizontal.p-splitter-resizing {
        cursor: col-resize;
        user-select: none;
    }

    .p-splitter-vertical.p-splitter-resizing {
        cursor: row-resize;
        user-select: none;
    }

    .p-splitter-horizontal > .p-splitter-gutter > .p-splitter-gutter-handle {
        height: dt('splitter.handle.size');
        width: 100%;
    }

    .p-splitter-vertical > .p-splitter-gutter > .p-splitter-gutter-handle {
        width: dt('splitter.handle.size');
        height: 100%;
    }

    .p-splitter-horizontal > .p-splitter-gutter {
        cursor: col-resize;
    }

    .p-splitter-vertical > .p-splitter-gutter {
        cursor: row-resize;
    }

    .p-splitterpanel {
        flex-grow: 1;
        overflow: hidden;
    }

    .p-splitterpanel-nested {
        display: flex;
    }

    .p-splitterpanel .p-splitter {
        flex-grow: 1;
        border: 0 none;
    }
`,U={root:function(e){var i=e.props;return["p-splitter p-component","p-splitter-"+i.layout]},gutter:"p-splitter-gutter",gutterHandle:"p-splitter-gutter-handle"},D=E.extend({name:"splitter",style:K,classes:U}),$={name:"BaseSplitter",extends:k,props:{layout:{type:String,default:"horizontal"},gutterSize:{type:Number,default:4},stateKey:{type:String,default:null},stateStorage:{type:String,default:"session"},step:{type:Number,default:5}},style:D,provide:function(){return{$pcSplitter:this,$parentInstance:this}}};function p(t){"@babel/helpers - typeof";return p=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},p(t)}function S(t,e,i){return(e=B(e))in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}function B(t){var e=O(t,"string");return p(e)=="symbol"?e:e+""}function O(t,e){if(p(t)!="object"||!t)return t;var i=t[Symbol.toPrimitive];if(i!==void 0){var r=i.call(t,e);if(p(r)!="object")return r;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}function y(t){return H(t)||F(t)||j(t)||N()}function N(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function j(t,e){if(t){if(typeof t=="string")return g(t,e);var i={}.toString.call(t).slice(8,-1);return i==="Object"&&t.constructor&&(i=t.constructor.name),i==="Map"||i==="Set"?Array.from(t):i==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(i)?g(t,e):void 0}}function F(t){if(typeof Symbol<"u"&&t[Symbol.iterator]!=null||t["@@iterator"]!=null)return Array.from(t)}function H(t){if(Array.isArray(t))return g(t)}function g(t,e){(e==null||e>t.length)&&(e=t.length);for(var i=0,r=Array(e);i<e;i++)r[i]=t[i];return r}var W={name:"Splitter",extends:$,inheritAttrs:!1,emits:["resizestart","resizeend","resize"],dragging:!1,mouseMoveListener:null,mouseUpListener:null,touchMoveListener:null,touchEndListener:null,size:null,gutterElement:null,startPos:null,prevPanelElement:null,nextPanelElement:null,nextPanelSize:null,prevPanelSize:null,panelSizes:null,prevPanelIndex:null,timer:null,data:function(){return{prevSize:null}},mounted:function(){this.initializePanels()},beforeUnmount:function(){this.clear(),this.unbindMouseListeners()},methods:{isSplitterPanel:function(e){return e.type.name==="SplitterPanel"},initializePanels:function(){var e=this;if(this.panels&&this.panels.length){var i=!1;if(this.isStateful()&&(i=this.restoreState()),!i){var r=y(this.$el.children).filter(function(n){return n.getAttribute("data-pc-name")==="splitterpanel"}),s=[];this.panels.map(function(n,a){var o=n.props&&w(n.props.size)?n.props.size:null,u=o??100/e.panels.length;s[a]=u,r[a].style.flexBasis="calc("+u+"% - "+(e.panels.length-1)*e.gutterSize+"px)"}),this.panelSizes=s,this.prevSize=parseFloat(s[0]).toFixed(4)}}},onResizeStart:function(e,i,r){this.gutterElement=e.currentTarget||e.target.parentElement,this.size=this.horizontal?M(this.$el):T(this.$el),r||(this.dragging=!0,this.startPos=this.layout==="horizontal"?e.pageX||e.changedTouches[0].pageX:e.pageY||e.changedTouches[0].pageY),this.prevPanelElement=this.gutterElement.previousElementSibling,this.nextPanelElement=this.gutterElement.nextElementSibling,r?(this.prevPanelSize=this.horizontal?c(this.prevPanelElement,!0):d(this.prevPanelElement,!0),this.nextPanelSize=this.horizontal?c(this.nextPanelElement,!0):d(this.nextPanelElement,!0)):(this.prevPanelSize=100*(this.horizontal?c(this.prevPanelElement,!0):d(this.prevPanelElement,!0))/this.size,this.nextPanelSize=100*(this.horizontal?c(this.nextPanelElement,!0):d(this.nextPanelElement,!0))/this.size),this.prevPanelIndex=i,this.$emit("resizestart",{originalEvent:e,sizes:this.panelSizes}),this.$refs.gutter[i].setAttribute("data-p-gutter-resizing",!0),this.$el.setAttribute("data-p-resizing",!0)},onResize:function(e,i,r){var s,n,a;r?this.horizontal?(n=100*(this.prevPanelSize+i)/this.size,a=100*(this.nextPanelSize-i)/this.size):(n=100*(this.prevPanelSize-i)/this.size,a=100*(this.nextPanelSize+i)/this.size):(this.horizontal?L(this.$el)?s=(this.startPos-e.pageX)*100/this.size:s=(e.pageX-this.startPos)*100/this.size:s=(e.pageY-this.startPos)*100/this.size,n=this.prevPanelSize+s,a=this.nextPanelSize-s),this.validateResize(n,a)||(n=Math.min(Math.max(this.prevPanelMinSize,n),100-this.nextPanelMinSize),a=Math.min(Math.max(this.nextPanelMinSize,a),100-this.prevPanelMinSize)),this.prevPanelElement.style.flexBasis="calc("+n+"% - "+(this.panels.length-1)*this.gutterSize+"px)",this.nextPanelElement.style.flexBasis="calc("+a+"% - "+(this.panels.length-1)*this.gutterSize+"px)",this.panelSizes[this.prevPanelIndex]=n,this.panelSizes[this.prevPanelIndex+1]=a,this.prevSize=parseFloat(n).toFixed(4),this.$emit("resize",{originalEvent:e,sizes:this.panelSizes})},onResizeEnd:function(e){this.isStateful()&&this.saveState(),this.$emit("resizeend",{originalEvent:e,sizes:this.panelSizes}),this.$refs.gutter.forEach(function(i){return i.setAttribute("data-p-gutter-resizing",!1)}),this.$el.setAttribute("data-p-resizing",!1),this.clear()},repeat:function(e,i,r){this.onResizeStart(e,i,!0),this.onResize(e,r,!0)},setTimer:function(e,i,r){var s=this;this.timer||(this.timer=setInterval(function(){s.repeat(e,i,r)},40))},clearTimer:function(){this.timer&&(clearInterval(this.timer),this.timer=null)},onGutterKeyUp:function(){this.clearTimer(),this.onResizeEnd()},onGutterKeyDown:function(e,i){switch(e.code){case"ArrowLeft":{this.layout==="horizontal"&&this.setTimer(e,i,this.step*-1),e.preventDefault();break}case"ArrowRight":{this.layout==="horizontal"&&this.setTimer(e,i,this.step),e.preventDefault();break}case"ArrowDown":{this.layout==="vertical"&&this.setTimer(e,i,this.step*-1),e.preventDefault();break}case"ArrowUp":{this.layout==="vertical"&&this.setTimer(e,i,this.step),e.preventDefault();break}}},onGutterMouseDown:function(e,i){this.onResizeStart(e,i),this.bindMouseListeners()},onGutterTouchStart:function(e,i){this.onResizeStart(e,i),this.bindTouchListeners(),e.preventDefault()},onGutterTouchMove:function(e){this.onResize(e),e.preventDefault()},onGutterTouchEnd:function(e){this.onResizeEnd(e),this.unbindTouchListeners(),e.preventDefault()},bindMouseListeners:function(){var e=this;this.mouseMoveListener||(this.mouseMoveListener=function(i){return e.onResize(i)},document.addEventListener("mousemove",this.mouseMoveListener)),this.mouseUpListener||(this.mouseUpListener=function(i){e.onResizeEnd(i),e.unbindMouseListeners()},document.addEventListener("mouseup",this.mouseUpListener))},bindTouchListeners:function(){var e=this;this.touchMoveListener||(this.touchMoveListener=function(i){return e.onResize(i.changedTouches[0])},document.addEventListener("touchmove",this.touchMoveListener)),this.touchEndListener||(this.touchEndListener=function(i){e.resizeEnd(i),e.unbindTouchListeners()},document.addEventListener("touchend",this.touchEndListener))},validateResize:function(e,i){return!(e>100||e<0||i>100||i<0||this.prevPanelMinSize>e||this.nextPanelMinSize>i)},unbindMouseListeners:function(){this.mouseMoveListener&&(document.removeEventListener("mousemove",this.mouseMoveListener),this.mouseMoveListener=null),this.mouseUpListener&&(document.removeEventListener("mouseup",this.mouseUpListener),this.mouseUpListener=null)},unbindTouchListeners:function(){this.touchMoveListener&&(document.removeEventListener("touchmove",this.touchMoveListener),this.touchMoveListener=null),this.touchEndListener&&(document.removeEventListener("touchend",this.touchEndListener),this.touchEndListener=null)},clear:function(){this.dragging=!1,this.size=null,this.startPos=null,this.prevPanelElement=null,this.nextPanelElement=null,this.prevPanelSize=null,this.nextPanelSize=null,this.gutterElement=null,this.prevPanelIndex=null},isStateful:function(){return this.stateKey!=null},getStorage:function(){switch(this.stateStorage){case"local":return window.localStorage;case"session":return window.sessionStorage;default:throw new Error(this.stateStorage+' is not a valid value for the state storage, supported values are "local" and "session".')}},saveState:function(){x(this.panelSizes)&&this.getStorage().setItem(this.stateKey,JSON.stringify(this.panelSizes))},restoreState:function(){var e=this,i=this.getStorage(),r=i.getItem(this.stateKey);if(r){this.panelSizes=JSON.parse(r);var s=y(this.$el.children).filter(function(n){return n.getAttribute("data-pc-name")==="splitterpanel"});return s.forEach(function(n,a){n.style.flexBasis="calc("+e.panelSizes[a]+"% - "+(e.panels.length-1)*e.gutterSize+"px)"}),!0}return!1},resetState:function(){this.initializePanels()}},computed:{panels:function(){var e=this,i=[];return this.$slots.default().forEach(function(r){e.isSplitterPanel(r)?i.push(r):r.children instanceof Array&&r.children.forEach(function(s){e.isSplitterPanel(s)&&i.push(s)})}),i},gutterStyle:function(){return this.horizontal?{width:this.gutterSize+"px"}:{height:this.gutterSize+"px"}},horizontal:function(){return this.layout==="horizontal"},getPTOptions:function(){var e;return{context:{nested:(e=this.$parentInstance)===null||e===void 0?void 0:e.nestedState}}},prevPanelMinSize:function(){var e=z(this.panels[this.prevPanelIndex],"minSize");return this.panels[this.prevPanelIndex].props&&e?e:0},nextPanelMinSize:function(){var e=z(this.panels[this.prevPanelIndex+1],"minSize");return this.panels[this.prevPanelIndex+1].props&&e?e:0},dataP:function(){var e;return P(S(S({},this.layout,this.layout),"nested",((e=this.$parentInstance)===null||e===void 0?void 0:e.nestedState)!=null))}}},X=["data-p"],C=["onMousedown","onTouchstart","onTouchmove","onTouchend","data-p"],V=["aria-orientation","aria-valuenow","onKeydown","data-p"];function Y(t,e,i,r,s,n){return h(),f("div",m({class:t.cx("root"),"data-p-resizing":!1,"data-p":n.dataP},t.ptmi("root",n.getPTOptions)),[(h(!0),f(v,null,A(n.panels,function(a,o){return h(),f(v,{key:o},[(h(),I(G(a),{tabindex:"-1"})),o!==n.panels.length-1?(h(),f("div",m({key:0,ref_for:!0,ref:"gutter",class:t.cx("gutter"),role:"separator",tabindex:"-1",onMousedown:function(l){return n.onGutterMouseDown(l,o)},onTouchstart:function(l){return n.onGutterTouchStart(l,o)},onTouchmove:function(l){return n.onGutterTouchMove(l,o)},onTouchend:function(l){return n.onGutterTouchEnd(l,o)},"data-p-gutter-resizing":!1,"data-p":n.dataP},{ref_for:!0},t.ptm("gutter")),[_("div",m({class:t.cx("gutterHandle"),tabindex:"0",style:[n.gutterStyle],"aria-orientation":t.layout,"aria-valuenow":s.prevSize,onKeyup:e[0]||(e[0]=function(){return n.onGutterKeyUp&&n.onGutterKeyUp.apply(n,arguments)}),onKeydown:function(l){return n.onGutterKeyDown(l,o)},"data-p":n.dataP},{ref_for:!0},t.ptm("gutterHandle")),null,16,V)],16,C)):R("",!0)],64)}),128))],16,X)}W.render=Y;export{W as default};
