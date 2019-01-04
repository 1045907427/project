(function(CNPrintDesigner){

    DesiginerToolbar = {

        commandButtonList: [
            {
                className:'button-text',
                text:designer_lang.text,
                menu:[
                    {
                        className:'thumb-horizontal-text',
                        text:designer_lang.horizontal_text,
                        onClick(ev){
                            CNPrintDesigner.Dispatcher.dispatch({
                                type:'INSERT_TEXT_COMMAND',
                                data:{
                                    orientation:"horizontal"
                                }
                            })
                        },
                    },
                    {
                        className:'thumb-vertical-text',
                        text:designer_lang.vertical_text,
                        onClick(ev){
                            CNPrintDesigner.Dispatcher.dispatch({
                                type:'INSERT_TEXT_COMMAND',
                                data:{
                                    orientation:"vertical"
                                }
                            })
                        }
                    }
                ]
            }, {
                onClick(ev){
                    CNPrintDesigner.Dispatcher.dispatch({
                        type:'INSERT_IMAGE_COMMAND'
                    })
                },
                className:'button-image',
                text:designer_lang.picture
            }, {
                className:'button-shape',
                text:designer_lang.shapes,
                menu:[
                    {
                        className:'thumb-horizontal-line',
                        text:designer_lang.horizontal_line,
                        onClick(ev){
                            CNPrintDesigner.Dispatcher.dispatch({
                                type:'INSERT_LINE_COMMAND',
                                data:{
                                    orientation:"horizontal"
                                }
                            })
                        },
                    },
                    {
                        className:'thumb-vertical-line',
                        text:designer_lang.vertical_line,
                        onClick(ev){
                            CNPrintDesigner.Dispatcher.dispatch({
                                type:'INSERT_LINE_COMMAND',
                                data:{
                                    orientation:"vertical"
                                }
                            })
                        }
                    },
                    {
                        className:'thumb-rect',
                        text:designer_lang.rectangle,
                        onClick(ev){
                            CNPrintDesigner.Dispatcher.dispatch({
                                type:'INSERT_RECT_COMMAND',
                                data:{
                                }
                            })
                        }
                    }
                ]
            }, {
                onClick(ev){
                    CNPrintDesigner.Dispatcher.dispatch({
                        type:'INSERT_QRCODE_COMMAND',
                        data:{
                            type: "qrcode"
                        }
                    })
                },
                className:'button-qrcode',
                text:designer_lang.barcode_2d
            }, {
                onClick(ev){
                    CNPrintDesigner.Dispatcher.dispatch({
                        type:'INSERT_BARCODE_COMMAND',
                        data:{
                            type: "code128"
                        }
                    })
                },
                className:'button-barcode',
                text:designer_lang.barcode
            }, {
                onClick(ev){
                    CNPrintDesigner.Dispatcher.dispatch({
                        type:'INSERT_COMPONENT_COMMAND'
                    })
                },
                className:'button-component',
                text:designer_lang.print_item
            }
        ],

        rightButtonList:[
            {
                onClick(ev){
                    CNPrintDesigner.EventManager.broadcast('TOGGLE_DESIGNER_EVENT', null)
                },
                hidden:true,
                className:'button-design',
                text:designer_lang.view_editer
            },
            {
                onClick(ev){
                    CNPrintDesigner.EventManager.broadcast('SAVE_COMMAND_EVENT', null)
                    // CNPrintDesigner.Dispatcher.dispatch({
                    //     type:'SAVE_COMMAND'
                    // });
                },
                className:'button-save',
                text:designer_lang.save
            },
            {
                onClick(ev){
                    CNPrintDesigner.Dispatcher.dispatch({
                        type:'RESOURCES_PUBLISH_COMMAND'
                    });
                },
                className:'button-publish',
                text:designer_lang.publish
            }
        ]
    }
}(CNPrintDesigner));