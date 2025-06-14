import{v as H,B as L,D as M,W as b,X as w,E as S,H as C,L as B,c as p,o as h,l as v,b as g,P as a,w as m,T as D,a as c}from"./CwV9-nPG.js";import{Z as f}from"./DGEOgZpe.js";import{C as O}from"./C3kXndTt.js";import{O as x}from"./QMdF3Z2_.js";import{s as E}from"./5bk1qhoj.js";import{s as R}from"./BQISPpyG.js";import"./BZ7rLBd_.js";var V=H`
    .p-colorpicker {
        display: inline-block;
        position: relative;
    }

    .p-colorpicker-dragging {
        cursor: pointer;
    }

    .p-colorpicker-preview {
        width: dt('colorpicker.preview.width');
        height: dt('colorpicker.preview.height');
        padding: 0;
        border: 0 none;
        border-radius: dt('colorpicker.preview.border.radius');
        transition:
            background dt('colorpicker.transition.duration'),
            color dt('colorpicker.transition.duration'),
            border-color dt('colorpicker.transition.duration'),
            outline-color dt('colorpicker.transition.duration'),
            box-shadow dt('colorpicker.transition.duration');
        outline-color: transparent;
        cursor: pointer;
    }

    .p-colorpicker-preview:enabled:focus-visible {
        border-color: dt('colorpicker.preview.focus.border.color');
        box-shadow: dt('colorpicker.preview.focus.ring.shadow');
        outline: dt('colorpicker.preview.focus.ring.width') dt('colorpicker.preview.focus.ring.style') dt('colorpicker.preview.focus.ring.color');
        outline-offset: dt('colorpicker.preview.focus.ring.offset');
    }

    .p-colorpicker-panel {
        background: dt('colorpicker.panel.background');
        border: 1px solid dt('colorpicker.panel.border.color');
        border-radius: dt('colorpicker.panel.border.radius');
        box-shadow: dt('colorpicker.panel.shadow');
        width: 193px;
        height: 166px;
        position: absolute;
        top: 0;
        left: 0;
    }

    .p-colorpicker-panel-inline {
        box-shadow: none;
        position: static;
    }

    .p-colorpicker-content {
        position: relative;
    }

    .p-colorpicker-color-selector {
        width: 150px;
        height: 150px;
        inset-block-start: 8px;
        inset-inline-start: 8px;
        position: absolute;
    }

    .p-colorpicker-color-background {
        width: 100%;
        height: 100%;
        background: linear-gradient(to top, #000 0%, rgba(0, 0, 0, 0) 100%), linear-gradient(to right, #fff 0%, rgba(255, 255, 255, 0) 100%);
    }

    .p-colorpicker-color-handle {
        position: absolute;
        inset-block-start: 0px;
        inset-inline-start: 150px;
        border-radius: 100%;
        width: 10px;
        height: 10px;
        border-width: 1px;
        border-style: solid;
        margin: -5px 0 0 -5px;
        cursor: pointer;
        opacity: 0.85;
        border-color: dt('colorpicker.handle.color');
    }

    .p-colorpicker-hue {
        width: 17px;
        height: 150px;
        inset-block-start: 8px;
        inset-inline-start: 167px;
        position: absolute;
        opacity: 0.85;
        background: linear-gradient(0deg, red 0, #ff0 17%, #0f0 33%, #0ff 50%, #00f 67%, #f0f 83%, red);
    }

    .p-colorpicker-hue-handle {
        position: absolute;
        inset-block-start: 150px;
        inset-inline-start: 0px;
        width: 21px;
        margin-inline-start: -2px;
        margin-block-start: -5px;
        height: 10px;
        border-width: 2px;
        border-style: solid;
        opacity: 0.85;
        cursor: pointer;
        border-color: dt('colorpicker.handle.color');
    }
`,I={root:"p-colorpicker p-component",preview:function(e){var i=e.props;return["p-colorpicker-preview",{"p-disabled":i.disabled}]},panel:function(e){var i=e.instance,r=e.props;return["p-colorpicker-panel",{"p-colorpicker-panel-inline":r.inline,"p-disabled":r.disabled,"p-invalid":i.$invalid}]},colorSelector:"p-colorpicker-color-selector",colorBackground:"p-colorpicker-color-background",colorHandle:"p-colorpicker-color-handle",hue:"p-colorpicker-hue",hueHandle:"p-colorpicker-hue-handle"},U=L.extend({name:"colorpicker",style:V,classes:I}),P={name:"BaseColorPicker",extends:R,props:{defaultColor:{type:null,default:"ff0000"},inline:{type:Boolean,default:!1},format:{type:String,default:"hex"},tabindex:{type:String,default:null},autoZIndex:{type:Boolean,default:!0},baseZIndex:{type:Number,default:0},appendTo:{type:[String,Object],default:"body"},inputId:{type:String,default:null},panelClass:null,overlayClass:null},style:U,provide:function(){return{$pcColorPicker:this,$parentInstance:this}}},T={name:"ColorPicker",extends:P,inheritAttrs:!1,emits:["change","show","hide"],data:function(){return{overlayVisible:!1}},hsbValue:null,localHue:null,outsideClickListener:null,documentMouseMoveListener:null,documentMouseUpListener:null,scrollHandler:null,resizeListener:null,hueDragging:null,colorDragging:null,selfUpdate:null,picker:null,colorSelector:null,colorHandle:null,hueView:null,hueHandle:null,watch:{modelValue:{immediate:!0,handler:function(e){this.hsbValue=this.toHSB(e),this.selfUpdate?this.selfUpdate=!1:this.updateUI()}}},beforeUnmount:function(){this.unbindOutsideClickListener(),this.unbindDragListeners(),this.unbindResizeListener(),this.scrollHandler&&(this.scrollHandler.destroy(),this.scrollHandler=null),this.picker&&this.autoZIndex&&f.clear(this.picker),this.clearRefs()},mounted:function(){this.updateUI()},methods:{pickColor:function(e){var i=this.colorSelector.getBoundingClientRect(),r=i.top+(window.pageYOffset||document.documentElement.scrollTop||document.body.scrollTop||0),s=i.left+document.body.scrollLeft,n=Math.floor(100*Math.max(0,Math.min(150,(e.pageX||e.changedTouches[0].pageX)-s))/150),l=Math.floor(100*(150-Math.max(0,Math.min(150,(e.pageY||e.changedTouches[0].pageY)-r)))/150);this.hsbValue=this.validateHSB({h:this.localHue,s:n,b:l}),this.selfUpdate=!0,this.updateColorHandle(),this.updateInput(),this.updateModel(e)},pickHue:function(e){var i=this.hueView.getBoundingClientRect().top+(window.pageYOffset||document.documentElement.scrollTop||document.body.scrollTop||0);this.localHue=Math.floor(360*(150-Math.max(0,Math.min(150,(e.pageY||e.changedTouches[0].pageY)-i)))/150),this.hsbValue=this.validateHSB({h:this.localHue,s:100,b:100}),this.selfUpdate=!0,this.updateColorSelector(),this.updateHue(),this.updateModel(e),this.updateInput()},updateModel:function(e){var i=this.d_value;switch(this.format){case"hex":i=this.HSBtoHEX(this.hsbValue);break;case"rgb":i=this.HSBtoRGB(this.hsbValue);break;case"hsb":i=this.hsbValue;break}this.writeValue(i,e),this.$emit("change",{event:e,value:i})},updateColorSelector:function(){if(this.colorSelector){var e=this.validateHSB({h:this.hsbValue.h,s:100,b:100});this.colorSelector.style.backgroundColor="#"+this.HSBtoHEX(e)}},updateColorHandle:function(){this.colorHandle&&(this.colorHandle.style.left=Math.floor(150*this.hsbValue.s/100)+"px",this.colorHandle.style.top=Math.floor(150*(100-this.hsbValue.b)/100)+"px")},updateHue:function(){this.hueHandle&&(this.hueHandle.style.top=Math.floor(150-150*this.hsbValue.h/360)+"px")},updateInput:function(){this.$refs.input&&(this.$refs.input.style.backgroundColor="#"+this.HSBtoHEX(this.hsbValue))},updateUI:function(){this.updateHue(),this.updateColorHandle(),this.updateInput(),this.updateColorSelector()},validateHSB:function(e){return{h:Math.min(360,Math.max(0,e.h)),s:Math.min(100,Math.max(0,e.s)),b:Math.min(100,Math.max(0,e.b))}},validateRGB:function(e){return{r:Math.min(255,Math.max(0,e.r)),g:Math.min(255,Math.max(0,e.g)),b:Math.min(255,Math.max(0,e.b))}},validateHEX:function(e){var i=6-e.length;if(i>0){for(var r=[],s=0;s<i;s++)r.push("0");r.push(e),e=r.join("")}return e},HEXtoRGB:function(e){var i=parseInt(e.indexOf("#")>-1?e.substring(1):e,16);return{r:i>>16,g:(i&65280)>>8,b:i&255}},HEXtoHSB:function(e){return this.RGBtoHSB(this.HEXtoRGB(e))},RGBtoHSB:function(e){var i={h:0,s:0,b:0},r=Math.min(e.r,e.g,e.b),s=Math.max(e.r,e.g,e.b),n=s-r;return i.b=s,i.s=s!==0?255*n/s:0,i.s!==0?e.r===s?i.h=(e.g-e.b)/n:e.g===s?i.h=2+(e.b-e.r)/n:i.h=4+(e.r-e.g)/n:i.h=-1,i.h*=60,i.h<0&&(i.h+=360),i.s*=100/255,i.b*=100/255,i},HSBtoRGB:function(e){var i={r:null,g:null,b:null},r=Math.round(e.h),s=Math.round(e.s*255/100),n=Math.round(e.b*255/100);if(s===0)i={r:n,g:n,b:n};else{var l=n,o=(255-s)*n/255,u=(l-o)*(r%60)/60;r===360&&(r=0),r<60?(i.r=l,i.b=o,i.g=o+u):r<120?(i.g=l,i.b=o,i.r=l-u):r<180?(i.g=l,i.r=o,i.b=o+u):r<240?(i.b=l,i.r=o,i.g=l-u):r<300?(i.b=l,i.g=o,i.r=o+u):r<360?(i.r=l,i.g=o,i.b=l-u):(i.r=0,i.g=0,i.b=0)}return{r:Math.round(i.r),g:Math.round(i.g),b:Math.round(i.b)}},RGBtoHEX:function(e){var i=[e.r.toString(16),e.g.toString(16),e.b.toString(16)];for(var r in i)i[r].length===1&&(i[r]="0"+i[r]);return i.join("")},HSBtoHEX:function(e){return this.RGBtoHEX(this.HSBtoRGB(e))},toHSB:function(e){var i;if(e)switch(this.format){case"hex":i=this.HEXtoHSB(e);break;case"rgb":i=this.RGBtoHSB(e);break;case"hsb":i=e;break}else i=this.HEXtoHSB(this.defaultColor);return this.localHue==null||!this.overlayVisible?this.localHue=i.h:i.h=this.localHue,i},onOverlayEnter:function(e){this.updateUI(),this.alignOverlay(),this.bindOutsideClickListener(),this.bindScrollListener(),this.bindResizeListener(),this.autoZIndex&&f.set("overlay",e,this.baseZIndex,this.$primevue.config.zIndex.overlay),this.$attrSelector&&e.setAttribute(this.$attrSelector,""),this.$emit("show")},onOverlayLeave:function(){this.unbindOutsideClickListener(),this.unbindScrollListener(),this.unbindResizeListener(),this.clearRefs(),this.$emit("hide")},onOverlayAfterLeave:function(e){this.autoZIndex&&f.clear(e)},alignOverlay:function(){this.appendTo==="self"?S(this.picker,this.$refs.input):C(this.picker,this.$refs.input)},onInputClick:function(){this.disabled||(this.overlayVisible=!this.overlayVisible)},onInputKeydown:function(e){switch(e.code){case"Space":this.overlayVisible=!this.overlayVisible,e.preventDefault();break;case"Escape":case"Tab":this.overlayVisible=!1;break}},onInputBlur:function(e){var i,r;(i=(r=this.formField).onBlur)===null||i===void 0||i.call(r)},onColorMousedown:function(e){this.disabled||(this.bindDragListeners(),this.onColorDragStart(e))},onColorDragStart:function(e){this.disabled||(this.colorDragging=!0,this.pickColor(e),this.$el.setAttribute("p-colorpicker-dragging","true"),!this.isUnstyled&&b(this.$el,"p-colorpicker-dragging"),e.preventDefault())},onDrag:function(e){this.colorDragging&&(this.pickColor(e),e.preventDefault()),this.hueDragging&&(this.pickHue(e),e.preventDefault())},onDragEnd:function(){this.colorDragging=!1,this.hueDragging=!1,this.$el.setAttribute("p-colorpicker-dragging","false"),!this.isUnstyled&&w(this.$el,"p-colorpicker-dragging"),this.unbindDragListeners()},onHueMousedown:function(e){this.disabled||(this.bindDragListeners(),this.onHueDragStart(e))},onHueDragStart:function(e){this.disabled||(this.hueDragging=!0,this.pickHue(e),!this.isUnstyled&&b(this.$el,"p-colorpicker-dragging"),e.preventDefault())},isInputClicked:function(e){return this.$refs.input&&this.$refs.input.isSameNode(e.target)},bindDragListeners:function(){this.bindDocumentMouseMoveListener(),this.bindDocumentMouseUpListener()},unbindDragListeners:function(){this.unbindDocumentMouseMoveListener(),this.unbindDocumentMouseUpListener()},bindOutsideClickListener:function(){var e=this;this.outsideClickListener||(this.outsideClickListener=function(i){e.overlayVisible&&e.picker&&!e.picker.contains(i.target)&&!e.isInputClicked(i)&&(e.overlayVisible=!1)},document.addEventListener("click",this.outsideClickListener,!0))},unbindOutsideClickListener:function(){this.outsideClickListener&&(document.removeEventListener("click",this.outsideClickListener,!0),this.outsideClickListener=null)},bindScrollListener:function(){var e=this;this.scrollHandler||(this.scrollHandler=new O(this.$refs.container,function(){e.overlayVisible&&(e.overlayVisible=!1)})),this.scrollHandler.bindScrollListener()},unbindScrollListener:function(){this.scrollHandler&&this.scrollHandler.unbindScrollListener()},bindResizeListener:function(){var e=this;this.resizeListener||(this.resizeListener=function(){e.overlayVisible&&!M()&&(e.overlayVisible=!1)},window.addEventListener("resize",this.resizeListener))},unbindResizeListener:function(){this.resizeListener&&(window.removeEventListener("resize",this.resizeListener),this.resizeListener=null)},bindDocumentMouseMoveListener:function(){this.documentMouseMoveListener||(this.documentMouseMoveListener=this.onDrag.bind(this),document.addEventListener("mousemove",this.documentMouseMoveListener))},unbindDocumentMouseMoveListener:function(){this.documentMouseMoveListener&&(document.removeEventListener("mousemove",this.documentMouseMoveListener),this.documentMouseMoveListener=null)},bindDocumentMouseUpListener:function(){this.documentMouseUpListener||(this.documentMouseUpListener=this.onDragEnd.bind(this),document.addEventListener("mouseup",this.documentMouseUpListener))},unbindDocumentMouseUpListener:function(){this.documentMouseUpListener&&(document.removeEventListener("mouseup",this.documentMouseUpListener),this.documentMouseUpListener=null)},pickerRef:function(e){this.picker=e},colorSelectorRef:function(e){this.colorSelector=e},colorHandleRef:function(e){this.colorHandle=e},hueViewRef:function(e){this.hueView=e},hueHandleRef:function(e){this.hueHandle=e},clearRefs:function(){this.picker=null,this.colorSelector=null,this.colorHandle=null,this.hueView=null,this.hueHandle=null},onOverlayClick:function(e){x.emit("overlay-click",{originalEvent:e,target:this.$el})}},components:{Portal:E}};function d(t){"@babel/helpers - typeof";return d=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},d(t)}function k(t,e){var i=Object.keys(t);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(t);e&&(r=r.filter(function(s){return Object.getOwnPropertyDescriptor(t,s).enumerable})),i.push.apply(i,r)}return i}function y(t){for(var e=1;e<arguments.length;e++){var i=arguments[e]!=null?arguments[e]:{};e%2?k(Object(i),!0).forEach(function(r){X(t,r,i[r])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(i)):k(Object(i)).forEach(function(r){Object.defineProperty(t,r,Object.getOwnPropertyDescriptor(i,r))})}return t}function X(t,e,i){return(e=j(e))in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}function j(t){var e=z(t,"string");return d(e)=="symbol"?e:e+""}function z(t,e){if(d(t)!="object"||!t)return t;var i=t[Symbol.toPrimitive];if(i!==void 0){var r=i.call(t,e);if(d(r)!="object")return r;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}var G=["id","tabindex","disabled"];function A(t,e,i,r,s,n){var l=B("Portal");return h(),p("div",a({ref:"container",class:t.cx("root")},t.ptmi("root")),[t.inline?v("",!0):(h(),p("input",a({key:0,ref:"input",id:t.inputId,type:"text",class:t.cx("preview"),readonly:"",tabindex:t.tabindex,disabled:t.disabled,onClick:e[0]||(e[0]=function(){return n.onInputClick&&n.onInputClick.apply(n,arguments)}),onKeydown:e[1]||(e[1]=function(){return n.onInputKeydown&&n.onInputKeydown.apply(n,arguments)}),onBlur:e[2]||(e[2]=function(){return n.onInputBlur&&n.onInputBlur.apply(n,arguments)})},t.ptm("preview")),null,16,G)),g(l,{appendTo:t.appendTo,disabled:t.inline},{default:m(function(){return[g(D,a({name:"p-connected-overlay",onEnter:n.onOverlayEnter,onLeave:n.onOverlayLeave,onAfterLeave:n.onOverlayAfterLeave},t.ptm("transition")),{default:m(function(){return[t.inline||s.overlayVisible?(h(),p("div",a({key:0,ref:n.pickerRef,class:[t.cx("panel"),t.panelClass,t.overlayClass],onClick:e[11]||(e[11]=function(){return n.onOverlayClick&&n.onOverlayClick.apply(n,arguments)})},y(y({},t.ptm("panel")),t.ptm("overlay"))),[c("div",a({class:t.cx("content")},t.ptm("content")),[c("div",a({ref:n.colorSelectorRef,class:t.cx("colorSelector"),onMousedown:e[3]||(e[3]=function(o){return n.onColorMousedown(o)}),onTouchstart:e[4]||(e[4]=function(o){return n.onColorDragStart(o)}),onTouchmove:e[5]||(e[5]=function(o){return n.onDrag(o)}),onTouchend:e[6]||(e[6]=function(o){return n.onDragEnd()})},t.ptm("colorSelector")),[c("div",a({class:t.cx("colorBackground")},t.ptm("colorBackground")),[c("div",a({ref:n.colorHandleRef,class:t.cx("colorHandle")},t.ptm("colorHandle")),null,16)],16)],16),c("div",a({ref:n.hueViewRef,class:t.cx("hue"),onMousedown:e[7]||(e[7]=function(o){return n.onHueMousedown(o)}),onTouchstart:e[8]||(e[8]=function(o){return n.onHueDragStart(o)}),onTouchmove:e[9]||(e[9]=function(o){return n.onDrag(o)}),onTouchend:e[10]||(e[10]=function(o){return n.onDragEnd()})},t.ptm("hue")),[c("div",a({ref:n.hueHandleRef,class:t.cx("hueHandle")},t.ptm("hueHandle")),null,16)],16)],16)],16)):v("",!0)]}),_:1},16,["onEnter","onLeave","onAfterLeave"])]}),_:1},8,["appendTo","disabled"])],16)}T.render=A;export{T as default};
