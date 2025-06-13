import{c as m}from"./Be1fzYNM.js";import{v as b,B as p,a2 as v,aW as c,aX as y,aY as w,c as d,o as u,a as S,l as h,P as l}from"./DZN9uhBn.js";import{s as D}from"./DT7vfX-F.js";import"./1noTU6vz.js";var P=b`
    .p-slider {
        position: relative;
        background: dt('slider.track.background');
        border-radius: dt('slider.track.border.radius');
    }

    .p-slider-handle {
        cursor: grab;
        touch-action: none;
        user-select: none;
        display: flex;
        justify-content: center;
        align-items: center;
        height: dt('slider.handle.height');
        width: dt('slider.handle.width');
        background: dt('slider.handle.background');
        border-radius: dt('slider.handle.border.radius');
        transition:
            background dt('slider.transition.duration'),
            color dt('slider.transition.duration'),
            border-color dt('slider.transition.duration'),
            box-shadow dt('slider.transition.duration'),
            outline-color dt('slider.transition.duration');
        outline-color: transparent;
    }

    .p-slider-handle::before {
        content: '';
        width: dt('slider.handle.content.width');
        height: dt('slider.handle.content.height');
        display: block;
        background: dt('slider.handle.content.background');
        border-radius: dt('slider.handle.content.border.radius');
        box-shadow: dt('slider.handle.content.shadow');
        transition: background dt('slider.transition.duration');
    }

    .p-slider:not(.p-disabled) .p-slider-handle:hover {
        background: dt('slider.handle.hover.background');
    }

    .p-slider:not(.p-disabled) .p-slider-handle:hover::before {
        background: dt('slider.handle.content.hover.background');
    }

    .p-slider-handle:focus-visible {
        box-shadow: dt('slider.handle.focus.ring.shadow');
        outline: dt('slider.handle.focus.ring.width') dt('slider.handle.focus.ring.style') dt('slider.handle.focus.ring.color');
        outline-offset: dt('slider.handle.focus.ring.offset');
    }

    .p-slider-range {
        display: block;
        background: dt('slider.range.background');
        border-radius: dt('slider.track.border.radius');
    }

    .p-slider.p-slider-horizontal {
        height: dt('slider.track.size');
    }

    .p-slider-horizontal .p-slider-range {
        inset-block-start: 0;
        inset-inline-start: 0;
        height: 100%;
    }

    .p-slider-horizontal .p-slider-handle {
        inset-block-start: 50%;
        margin-block-start: calc(-1 * calc(dt('slider.handle.height') / 2));
        margin-inline-start: calc(-1 * calc(dt('slider.handle.width') / 2));
    }

    .p-slider-vertical {
        min-height: 100px;
        width: dt('slider.track.size');
    }

    .p-slider-vertical .p-slider-handle {
        inset-inline-start: 50%;
        margin-inline-start: calc(-1 * calc(dt('slider.handle.width') / 2));
        margin-block-end: calc(-1 * calc(dt('slider.handle.height') / 2));
    }

    .p-slider-vertical .p-slider-range {
        inset-block-end: 0;
        inset-inline-start: 0;
        width: 100%;
    }
`,k={handle:{position:"absolute"},range:{position:"absolute"}},L={root:function(e){var i=e.instance,a=e.props;return["p-slider p-component",{"p-disabled":a.disabled,"p-invalid":i.$invalid,"p-slider-horizontal":a.orientation==="horizontal","p-slider-vertical":a.orientation==="vertical"}]},range:"p-slider-range",handle:"p-slider-handle"},E=p.extend({name:"slider",style:P,classes:L,inlineStyles:k}),B={name:"BaseSlider",extends:D,props:{min:{type:Number,default:0},max:{type:Number,default:100},orientation:{type:String,default:"horizontal"},step:{type:Number,default:null},range:{type:Boolean,default:!1},tabindex:{type:Number,default:0},ariaLabelledby:{type:String,default:null},ariaLabel:{type:String,default:null}},style:E,provide:function(){return{$pcSlider:this,$parentInstance:this}}};function o(t){"@babel/helpers - typeof";return o=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},o(t)}function M(t,e,i){return(e=A(e))in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}function A(t){var e=z(t,"string");return o(e)=="symbol"?e:e+""}function z(t,e){if(o(t)!="object"||!t)return t;var i=t[Symbol.toPrimitive];if(i!==void 0){var a=i.call(t,e);if(o(a)!="object")return a;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(t)}function T(t){return I(t)||H(t)||x(t)||V()}function V(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function x(t,e){if(t){if(typeof t=="string")return f(t,e);var i={}.toString.call(t).slice(8,-1);return i==="Object"&&t.constructor&&(i=t.constructor.name),i==="Map"||i==="Set"?Array.from(t):i==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(i)?f(t,e):void 0}}function H(t){if(typeof Symbol<"u"&&t[Symbol.iterator]!=null||t["@@iterator"]!=null)return Array.from(t)}function I(t){if(Array.isArray(t))return f(t)}function f(t,e){(e==null||e>t.length)&&(e=t.length);for(var i=0,a=Array(e);i<e;i++)a[i]=t[i];return a}var K={name:"Slider",extends:B,inheritAttrs:!1,emits:["change","slideend"],dragging:!1,handleIndex:null,initX:null,initY:null,barWidth:null,barHeight:null,dragListener:null,dragEndListener:null,beforeUnmount:function(){this.unbindDragListeners()},methods:{updateDomData:function(){var e=this.$el.getBoundingClientRect();this.initX=e.left+y(),this.initY=e.top+w(),this.barWidth=this.$el.offsetWidth,this.barHeight=this.$el.offsetHeight},setValue:function(e){var i,a=e.touches?e.touches[0].pageX:e.pageX,s=e.touches?e.touches[0].pageY:e.pageY;this.orientation==="horizontal"?c(this.$el)?i=(this.initX+this.barWidth-a)*100/this.barWidth:i=(a-this.initX)*100/this.barWidth:i=(this.initY+this.barHeight-s)*100/this.barHeight;var n=(this.max-this.min)*(i/100)+this.min;if(this.step){var r=this.range?this.value[this.handleIndex]:this.value,g=n-r;g<0?n=r+Math.ceil(n/this.step-r/this.step)*this.step:g>0&&(n=r+Math.floor(n/this.step-r/this.step)*this.step)}else n=Math.floor(n);this.updateModel(e,n)},updateModel:function(e,i){var a=Math.round(i*100)/100,s;this.range?(s=this.value?T(this.value):[],this.handleIndex==0?(a<this.min?a=this.min:a>=this.max&&(a=this.max),s[0]=a):(a>this.max?a=this.max:a<=this.min&&(a=this.min),s[1]=a)):(a<this.min?a=this.min:a>this.max&&(a=this.max),s=a),this.writeValue(s,e),this.$emit("change",s)},onDragStart:function(e,i){this.disabled||(this.$el.setAttribute("data-p-sliding",!0),this.dragging=!0,this.updateDomData(),this.range&&this.value[0]===this.max?this.handleIndex=0:this.handleIndex=i,e.currentTarget.focus())},onDrag:function(e){this.dragging&&this.setValue(e)},onDragEnd:function(e){this.dragging&&(this.dragging=!1,this.$el.setAttribute("data-p-sliding",!1),this.$emit("slideend",{originalEvent:e,value:this.value}))},onBarClick:function(e){this.disabled||v(e.target,"data-pc-section")!=="handle"&&(this.updateDomData(),this.setValue(e))},onMouseDown:function(e,i){this.bindDragListeners(),this.onDragStart(e,i)},onKeyDown:function(e,i){switch(this.handleIndex=i,e.code){case"ArrowDown":case"ArrowLeft":this.decrementValue(e,i),e.preventDefault();break;case"ArrowUp":case"ArrowRight":this.incrementValue(e,i),e.preventDefault();break;case"PageDown":this.decrementValue(e,i,!0),e.preventDefault();break;case"PageUp":this.incrementValue(e,i,!0),e.preventDefault();break;case"Home":this.updateModel(e,this.min),e.preventDefault();break;case"End":this.updateModel(e,this.max),e.preventDefault();break}},onBlur:function(e,i){var a,s;(a=(s=this.formField).onBlur)===null||a===void 0||a.call(s,e)},decrementValue:function(e,i){var a=arguments.length>2&&arguments[2]!==void 0?arguments[2]:!1,s;this.range?this.step?s=this.value[i]-this.step:s=this.value[i]-1:this.step?s=this.value-this.step:!this.step&&a?s=this.value-10:s=this.value-1,this.updateModel(e,s),e.preventDefault()},incrementValue:function(e,i){var a=arguments.length>2&&arguments[2]!==void 0?arguments[2]:!1,s;this.range?this.step?s=this.value[i]+this.step:s=this.value[i]+1:this.step?s=this.value+this.step:!this.step&&a?s=this.value+10:s=this.value+1,this.updateModel(e,s),e.preventDefault()},bindDragListeners:function(){this.dragListener||(this.dragListener=this.onDrag.bind(this),document.addEventListener("mousemove",this.dragListener)),this.dragEndListener||(this.dragEndListener=this.onDragEnd.bind(this),document.addEventListener("mouseup",this.dragEndListener))},unbindDragListeners:function(){this.dragListener&&(document.removeEventListener("mousemove",this.dragListener),this.dragListener=null),this.dragEndListener&&(document.removeEventListener("mouseup",this.dragEndListener),this.dragEndListener=null)},rangeStyle:function(){if(this.range){var e=this.rangeEndPosition>this.rangeStartPosition?this.rangeEndPosition-this.rangeStartPosition:this.rangeStartPosition-this.rangeEndPosition,i=this.rangeEndPosition>this.rangeStartPosition?this.rangeStartPosition:this.rangeEndPosition;return this.horizontal?{"inset-inline-start":i+"%",width:e+"%"}:{bottom:i+"%",height:e+"%"}}else return this.horizontal?{width:this.handlePosition+"%"}:{height:this.handlePosition+"%"}},handleStyle:function(){return this.horizontal?{"inset-inline-start":this.handlePosition+"%"}:{bottom:this.handlePosition+"%"}},rangeStartHandleStyle:function(){return this.horizontal?{"inset-inline-start":this.rangeStartPosition+"%"}:{bottom:this.rangeStartPosition+"%"}},rangeEndHandleStyle:function(){return this.horizontal?{"inset-inline-start":this.rangeEndPosition+"%"}:{bottom:this.rangeEndPosition+"%"}}},computed:{value:function(){var e;if(this.range){var i,a,s,n;return[(i=(a=this.d_value)===null||a===void 0?void 0:a[0])!==null&&i!==void 0?i:this.min,(s=(n=this.d_value)===null||n===void 0?void 0:n[1])!==null&&s!==void 0?s:this.max]}return(e=this.d_value)!==null&&e!==void 0?e:this.min},horizontal:function(){return this.orientation==="horizontal"},vertical:function(){return this.orientation==="vertical"},handlePosition:function(){return this.value<this.min?0:this.value>this.max?100:(this.value-this.min)*100/(this.max-this.min)},rangeStartPosition:function(){return this.value&&this.value[0]!==void 0?this.value[0]<this.min?0:(this.value[0]-this.min)*100/(this.max-this.min):0},rangeEndPosition:function(){return this.value&&this.value.length===2&&this.value[1]!==void 0?this.value[1]>this.max?100:(this.value[1]-this.min)*100/(this.max-this.min):100},dataP:function(){return m(M({},this.orientation,this.orientation))}}},C=["data-p"],W=["data-p"],X=["tabindex","aria-valuemin","aria-valuenow","aria-valuemax","aria-labelledby","aria-label","aria-orientation","data-p"],N=["tabindex","aria-valuemin","aria-valuenow","aria-valuemax","aria-labelledby","aria-label","aria-orientation","data-p"],Y=["tabindex","aria-valuemin","aria-valuenow","aria-valuemax","aria-labelledby","aria-label","aria-orientation","data-p"];function j(t,e,i,a,s,n){return u(),d("div",l({class:t.cx("root"),onClick:e[18]||(e[18]=function(){return n.onBarClick&&n.onBarClick.apply(n,arguments)})},t.ptmi("root"),{"data-p-sliding":!1,"data-p":n.dataP}),[S("span",l({class:t.cx("range"),style:[t.sx("range"),n.rangeStyle()]},t.ptm("range"),{"data-p":n.dataP}),null,16,W),t.range?h("",!0):(u(),d("span",l({key:0,class:t.cx("handle"),style:[t.sx("handle"),n.handleStyle()],onTouchstartPassive:e[0]||(e[0]=function(r){return n.onDragStart(r)}),onTouchmovePassive:e[1]||(e[1]=function(r){return n.onDrag(r)}),onTouchend:e[2]||(e[2]=function(r){return n.onDragEnd(r)}),onMousedown:e[3]||(e[3]=function(r){return n.onMouseDown(r)}),onKeydown:e[4]||(e[4]=function(r){return n.onKeyDown(r)}),onBlur:e[5]||(e[5]=function(r){return n.onBlur(r)}),tabindex:t.tabindex,role:"slider","aria-valuemin":t.min,"aria-valuenow":t.d_value,"aria-valuemax":t.max,"aria-labelledby":t.ariaLabelledby,"aria-label":t.ariaLabel,"aria-orientation":t.orientation},t.ptm("handle"),{"data-p":n.dataP}),null,16,X)),t.range?(u(),d("span",l({key:1,class:t.cx("handle"),style:[t.sx("handle"),n.rangeStartHandleStyle()],onTouchstartPassive:e[6]||(e[6]=function(r){return n.onDragStart(r,0)}),onTouchmovePassive:e[7]||(e[7]=function(r){return n.onDrag(r)}),onTouchend:e[8]||(e[8]=function(r){return n.onDragEnd(r)}),onMousedown:e[9]||(e[9]=function(r){return n.onMouseDown(r,0)}),onKeydown:e[10]||(e[10]=function(r){return n.onKeyDown(r,0)}),onBlur:e[11]||(e[11]=function(r){return n.onBlur(r,0)}),tabindex:t.tabindex,role:"slider","aria-valuemin":t.min,"aria-valuenow":t.d_value?t.d_value[0]:null,"aria-valuemax":t.max,"aria-labelledby":t.ariaLabelledby,"aria-label":t.ariaLabel,"aria-orientation":t.orientation},t.ptm("startHandler"),{"data-p":n.dataP}),null,16,N)):h("",!0),t.range?(u(),d("span",l({key:2,class:t.cx("handle"),style:[t.sx("handle"),n.rangeEndHandleStyle()],onTouchstartPassive:e[12]||(e[12]=function(r){return n.onDragStart(r,1)}),onTouchmovePassive:e[13]||(e[13]=function(r){return n.onDrag(r)}),onTouchend:e[14]||(e[14]=function(r){return n.onDragEnd(r)}),onMousedown:e[15]||(e[15]=function(r){return n.onMouseDown(r,1)}),onKeydown:e[16]||(e[16]=function(r){return n.onKeyDown(r,1)}),onBlur:e[17]||(e[17]=function(r){return n.onBlur(r,1)}),tabindex:t.tabindex,role:"slider","aria-valuemin":t.min,"aria-valuenow":t.d_value?t.d_value[1]:null,"aria-valuemax":t.max,"aria-labelledby":t.ariaLabelledby,"aria-label":t.ariaLabel,"aria-orientation":t.orientation},t.ptm("endHandler"),{"data-p":n.dataP}),null,16,Y)):h("",!0)],16,C)}K.render=j;export{K as default};
